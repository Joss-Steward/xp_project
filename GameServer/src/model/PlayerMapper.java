package model;

import java.util.ArrayList;

import data.AdventureStateRecord;
import data.QuestStateRecord;
import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.AdventureStateTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.PlayerConnectionRowDataGateway;
import datasource.PlayerConnectionRowDataGatewayMock;
import datasource.PlayerConnectionRowDataGatewayRDS;
import datasource.PlayerRowDataGateway;
import datasource.PlayerRowDataGatewayMock;
import datasource.PlayerRowDataGatewayRDS;
import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayMock;
import datasource.QuestStateTableDataGatewayRDS;
import model.OptionsManager;
import model.PlayerLogin;

/**
 * 
 * Manages retrieving and persisting all of the data associated with Players
 * (basically, connector between the Player class and the gateways in the data
 * source)
 * 
 * @author Merlin
 *
 */
public class PlayerMapper
{

	private PlayerRowDataGateway playerGateway;
	private PlayerConnectionRowDataGateway playerConnectionGateway;
	private QuestStateTableDataGateway questStateGateway;
	private AdventureStateTableDataGateway adventureStateGateway;
	/**
	 * The player we are connecting to the gateways
	 */
	protected Player player;

	/**
	 * Finder constructor
	 * 
	 * @param playerID
	 *            the player's unique ID
	 * @throws DatabaseException
	 *             if we can't find the given player
	 */
	public PlayerMapper(int playerID) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isTestMode())
		{
			this.playerGateway = new PlayerRowDataGatewayMock(playerID);
			this.questStateGateway = QuestStateTableDataGatewayMock.getSingleton();
			this.adventureStateGateway = AdventureStateTableDataGatewayMock
					.getSingleton();
			this.playerConnectionGateway = new PlayerConnectionRowDataGatewayMock(
					playerID);
		} else
		{
			this.playerGateway = new PlayerRowDataGatewayRDS(playerID);
			this.questStateGateway = QuestStateTableDataGatewayRDS.getSingleton();
			this.adventureStateGateway = AdventureStateTableDataGatewayRDS.getSingleton();
			this.playerConnectionGateway = new PlayerConnectionRowDataGatewayRDS(playerID);
		}
		this.player = createPlayerObject();
		player.setPlayerLogin(new PlayerLogin(playerID));
		player.setAppearanceType(playerGateway.getAppearanceType());
		player.setPlayerPositionWithoutNotifying(playerGateway.getPosition());
		player.setQuizScore(playerGateway.getQuizScore());
		player.setPlayerID(playerID);
		player.setCrew(playerGateway.getCrew());

		player.setExperiencePoints(playerGateway.getExperiencePoints());
		player.setDataMapper(this);
		player.setMapName(playerConnectionGateway.getMapName());
		loadQuestStates();
//		player.sendReportGivingPosition();
	}

	private void loadQuestStates() throws DatabaseException
	{
		ArrayList<QuestStateRecord> questStateRecords = questStateGateway
				.getQuestStates(player.getPlayerID());
		for (QuestStateRecord qsRec : questStateRecords)
		{
			QuestState questState = new QuestState(player.getPlayerID(),
					qsRec.getQuestID(), qsRec.getState(), qsRec.isNeedingNotification());
			ArrayList<AdventureStateRecord> adventureStateRecords = adventureStateGateway
					.getAdventureStates(player.getPlayerID(), qsRec.getQuestID());
			ArrayList<AdventureState> adventureStates = new ArrayList<AdventureState>();
			for (AdventureStateRecord asRec : adventureStateRecords)
			{
				adventureStates.add(new AdventureState(asRec.getAdventureID(), asRec
						.getState(), asRec.isNeedingNotification()));
			}
			questState.addAdventures(adventureStates);
			QuestManager.getSingleton().addQuestState(player.getPlayerID(), questState);
		}

	}

	/**
	 * @return a new object of the type this mapper is managing
	 */
	protected Player createPlayerObject()
	{
		return new Player();
	}

	/**
	 * Get the player that this Mapper is responsible for
	 * 
	 * @return the player this mapper manages
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Persist the current state of the player into the data source
	 * 
	 * @throws DatabaseException
	 *             if we can't complete the write
	 * @throws IllegalQuestChangeException
	 *             shouldn't
	 */
	public void persist() throws DatabaseException, IllegalQuestChangeException
	{

		playerGateway.setAppearanceType(player.getAppearanceType());
		playerGateway.setPosition(player.getPlayerPosition());
		playerGateway.setQuizScore(player.getQuizScore());
		playerGateway.setExperiencePoints(player.getExperiencePoints());
		playerGateway.setCrew(player.getCrew());

		playerGateway.persist();

		playerConnectionGateway.storeMapName(player.getMapName());

		ArrayList<QuestState> questList = QuestManager.getSingleton().getQuestList(
				player.getPlayerID());
		if (questList != null)
		{
			for (QuestState quest : questList)
			{
				questStateGateway.udpateState(player.getPlayerID(), quest.getID(),
						quest.getStateValue(), quest.isNeedingNotification());
				for (AdventureState a : quest.getAdventureList())
				{
					adventureStateGateway.updateState(player.getPlayerID(),
							quest.getID(), a.getID(), a.getState(),
							a.isNeedingNotification());
				}
			}
		}
	}

	/**
	 * Removes the player from data areas other than the PlayerManager
	 */
	public void removePlayer()
	{
		QuestManager.getSingleton().removeQuestStatesForPlayer(player.getPlayerID());
	}

}
