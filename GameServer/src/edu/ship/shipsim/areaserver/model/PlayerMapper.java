package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;

import datasource.DatabaseException;
import model.OptionsManager;
import model.PlayerLogin;
import edu.ship.shipsim.areaserver.datasource.AdventureStateRecord;
import edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGateway;
import edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.QuestStateRecord;
import edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGateway;
import edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGatewayRDS;

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
		} else
		{
			this.playerGateway = new PlayerRowDataGatewayRDS(playerID);
			this.questStateGateway = QuestStateTableDataGatewayRDS.getSingleton();
			this.adventureStateGateway = AdventureStateTableDataGatewayRDS.getSingleton();
		}
		this.player = createPlayerObject();
		player.setAppearanceType(playerGateway.getAppearanceType());
		player.setPlayerPositionWithoutNotifying(playerGateway.getPosition());
		player.setQuizScore(playerGateway.getQuizScore());
		player.setPlayerLogin(new PlayerLogin(playerID));
		player.setPlayerID(playerID);
		player.setMapName(playerGateway.getMapName());
		player.setDataMapper(this);
		loadQuestStates();
	}

	private void loadQuestStates() throws DatabaseException
	{
		ArrayList<QuestStateRecord> questStateRecords = questStateGateway
				.getQuestStates(player.getPlayerID());
		for (QuestStateRecord qsRec : questStateRecords)
		{
			QuestState questState = new QuestState(qsRec.getQuestID(), qsRec.getState());
			ArrayList<AdventureStateRecord> adventureStateRecords = adventureStateGateway
					.getAdventureStates(player.getPlayerID(), qsRec.getQuestID());
			ArrayList<AdventureState> adventureStates = new ArrayList<AdventureState>();
			for (AdventureStateRecord asRec : adventureStateRecords)
			{
				adventureStates.add(new AdventureState(asRec.getAdventureID(), asRec
						.getState()));
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
	 */
	public void persist() throws DatabaseException
	{
		playerGateway.setAppearanceType(player.getAppearanceType());
		playerGateway.setMapName(player.getMapName());
		playerGateway.setPosition(player.getPlayerPosition());
		playerGateway.setQuizScore(player.getQuizScore());
		playerGateway.persist();
		ArrayList<QuestState> questList = QuestManager.getSingleton().getQuestList(
				player.getPlayerID());
		if (questList != null)
		{
			for (QuestState quest : questList)
			{
				questStateGateway.udpateState(player.getPlayerID(), quest.getID(),
						quest.getStateValue());
				for (AdventureState a : quest.getAdventureList())
				{
					adventureStateGateway.updateState(player.getPlayerID(),
							quest.getID(), a.getID(), a.getState());
				}
			}
		}
	}

}
