package edu.ship.shipsim.areaserver.model.reports;

import java.util.ArrayList;

import datasource.DatabaseException;
import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import edu.ship.shipsim.areaserver.model.AdventureState;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.Quest;
import edu.ship.shipsim.areaserver.model.QuestManager;
import edu.ship.shipsim.areaserver.model.QuestState;

/**
 * Report that combines Quest descriptions and Quest states
 * @author Ryan
 *
 */
public class CurrentQuestStateReport 
{

	private ArrayList<ClientPlayerQuest> clientPlayerQuestList = new ArrayList<ClientPlayerQuest>();
	private ArrayList<QuestState> questStateList = new ArrayList<QuestState>();
	
	/**
	 * Combine the player's quest state and quest descriptions
	 * @param player the player
	 * @throws DatabaseException shouldn't
	 */
	public CurrentQuestStateReport(Player player) throws DatabaseException 
	{
		this.questStateList = player.getQuestList();
		combineQuest();
	}
	
	/**
	 * Combines a quest description and state and
	 * adds them to clientPlayerQuestList
	 * @throws DatabaseException
	 */
	private void combineQuest() throws DatabaseException 
	{
		for(QuestState qs: questStateList)
		{
			int questID = qs.getID();
			Quest quest = QuestManager.getSingleton().getQuest(questID);
			
			ClientPlayerQuest clientQuest = new ClientPlayerQuest(quest.getQuestID(), quest.getDescription(), qs.getStateValue());
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
			ca.add(new ClientPlayerAdventure(a.getID(),quest.getAdventureDescription(adventureID), a.getState()));
		}
		
		return ca;
	}

	/**
	 * Return the player's quest state list
	 * @return questStateList
	 */
	public ArrayList<QuestState> getQuestStateList()
	{
		return questStateList;
	}

	/**
	 * Return ArrayList of Client Player Quests
	 * @return clientPlayerQuestList
	 */
	public ArrayList<ClientPlayerQuest> getClientPlayerQuestList() {
		return clientPlayerQuestList;
	}
}
