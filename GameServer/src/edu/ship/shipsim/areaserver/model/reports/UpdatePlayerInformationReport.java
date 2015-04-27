package edu.ship.shipsim.areaserver.model.reports;

import java.util.ArrayList;

import datasource.DatabaseException;
import datasource.LevelRecord;
import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.QualifiedObservableReport;
import edu.ship.shipsim.areaserver.model.AdventureState;
import edu.ship.shipsim.areaserver.model.LevelManager;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.Quest;
import edu.ship.shipsim.areaserver.model.QuestManager;
import edu.ship.shipsim.areaserver.model.QuestState;

/**
 * Report that combines Quest descriptions and Quest states
 * @author Ryan, LaVonne, Olivia
 *
 */
public class UpdatePlayerInformationReport implements QualifiedObservableReport
{

	private ArrayList<ClientPlayerQuest> clientPlayerQuestList = new ArrayList<ClientPlayerQuest>();
	private int experiencePoints;
	private LevelRecord level;
	private int playerID;
	
	
	/**
	 * Combine the player's quest state and quest descriptions
	 * Sets local experience points equal to player's experience points
	 * @param player the player
	 * @throws DatabaseException shouldn't
	 */
	public UpdatePlayerInformationReport(Player player) throws DatabaseException 
	{
		combineQuest(QuestManager.getSingleton().getQuestList(player.getPlayerID()));
		this.experiencePoints = player.getExperiencePoints();
		this.level = LevelManager.getSingleton().getLevelForPoints(experiencePoints);
		this.playerID = player.getPlayerID();
	}
	
	/**
	 * Combines a quest description and state and
	 * adds them to clientPlayerQuestList
	 * @throws DatabaseException
	 */
	private void combineQuest(ArrayList<QuestState> questStateList) throws DatabaseException 
	{
		for(QuestState qs: questStateList)
		{
			int questID = qs.getID();
			Quest quest = QuestManager.getSingleton().getQuest(questID);
			
			ClientPlayerQuest clientQuest = new ClientPlayerQuest(quest.getQuestID(), quest.getDescription(), qs.getStateValue(), quest.getExperiencePointsGained()	
					, quest.getAdventuresForFulfillment());
			clientQuest.setAdventures(combineAdventure(quest, qs));
			
			clientPlayerQuestList.add(clientQuest);
		}
	}
	
	private ArrayList<ClientPlayerAdventure> combineAdventure(Quest quest, QuestState qs)
	{
		ArrayList<ClientPlayerAdventure> ca = new ArrayList<ClientPlayerAdventure>();
		for(AdventureState a: qs.getAdventureList())
		{
			int adventureID = a.getID();
			ca.add(new ClientPlayerAdventure(a.getID(),quest.getAdventureDescription(adventureID), quest.getAdventureXP(adventureID), a.getState(), a.isNeedingNotification()));
		}
		
		return ca;
	}


	/**
	 * Return ArrayList of Client Player Quests
	 * @return clientPlayerQuestList
	 */
	public ArrayList<ClientPlayerQuest> getClientPlayerQuestList() 
	{
		return clientPlayerQuestList;
	}

	/**
	 * Return int of Player's experience points
	 * @return experiencePoints
	 */
	public int getExperiencePts() 
	{
		return experiencePoints;
	}

	/**
	 * Returns the Player's level
	 * @return level
	 */
	public LevelRecord getLevel() 
	{
		return level;
	}

	/**
	 * @return this player's playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}
}
