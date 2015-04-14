package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;

import model.QualifiedObservableConnector;
import datasource.AdventureStateEnum;
import datasource.DatabaseException;
import datasource.QuestStateEnum;
import edu.ship.shipsim.areaserver.model.reports.QuestStateChangeReport;

/**
 * Stores the states of all the quests for an individual player on the server
 * 
 * @author Ryan
 *
 */
public class QuestState
{
	private int questID;
	private QuestStateEnum questState;
	private ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
	private int playerID;

	/**
	 * Constructs the QuestState
	 * 
	 * @param id
	 *            unique ID for the quest in the system
	 * @param questStateList
	 *            the quest's state for the player, can be either hidden,
	 *            available, triggered, fulfilled, completed
	 */
	public QuestState(int id, QuestStateEnum questStateList)
	{
		this.questID = id;
		this.questState = questStateList;
	}

	/**
	 * Assigns the quest's adventures using an ArrayList of adventures prepared
	 * already
	 * 
	 * @param adventureList
	 *            a list containing multiple adventures for a quest
	 */
	public void addAdventures(ArrayList<AdventureState> adventureList)
	{
		for (AdventureState adventure : adventureList)
		{
			this.adventureList.add(adventure);
			adventure.setParentQuest(this);
		}
	}

	/**
	 * Check to see if we have fulfilled this quest by completing enough
	 * adventures. If so, transition to the NEED_FULFILLED_NOTIFICATION state
	 * 
	 * @throws DatabaseException
	 *             if the datasource can't find the number of adventures
	 *             required for the quest
	 */
	public void checkForFulfillment() throws DatabaseException
	{
		if (questState == QuestStateEnum.TRIGGERED)
		{
			int adventuresComplete = 0;
			for (AdventureState state : adventureList)
			{
				if (state.getState() == AdventureStateEnum.NEED_NOTIFICATION
						|| state.getState() == AdventureStateEnum.COMPLETED)
				{
					adventuresComplete++;
				}
			}
			int adventuresRequired = QuestManager.getSingleton().getQuest(this.questID)
					.getAdventuresForFulfillment();
			if (adventuresComplete >= adventuresRequired)
			{
				questState = QuestStateEnum.NEED_FULFILLED_NOTIFICATION;
				QualifiedObservableConnector.getSingleton().sendReport(
						new QuestStateChangeReport(playerID, questID,
								QuestManager.getSingleton().getQuest(questID)
										.getDescription(), questState));
			}
		}
	}

	/**
	 * Returns the adventures in this quest
	 * 
	 * @return list of adventures
	 */
	public ArrayList<AdventureState> getAdventureList()
	{
		return adventureList;
	}

	/**
	 * Returns the quest's unique ID
	 * 
	 * @return questID the quest's unique ID
	 */
	public int getID()
	{
		return questID;
	}

	/**
	 * @return the ID of the player whose state this belongs to
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Returns the size of this quest's adventure list
	 * 
	 * @return the number of adventures this quest has
	 */
	public int getSizeOfAdventureList()
	{
		return this.adventureList.size();
	}

	/**
	 * Returns the quest's state
	 * 
	 * @return questState the state of the quest for a player
	 */
	public QuestStateEnum getStateValue()
	{
		return questState;
	}

	/**
	 * Tell this state which player it belongs to
	 * 
	 * @param playerID
	 *            the player's unique id
	 */
	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * Change the quest's state from hidden to available Also change all the
	 * quest's adventures from hidden to pending.
	 */
	public void trigger()
	{
		if (this.getStateValue().equals(QuestStateEnum.AVAILABLE))
		{
			this.questState = QuestStateEnum.TRIGGERED;
			for (AdventureState state : adventureList)
			{
				state.trigger();
			}
		}
	}
}
