package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;

/**
 * Stores the states of all the quests for an individual player on the server
 * @author Ryan
 *
 */
public class QuestState 
{
	private int questID;
	private String questState;
	private ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
	
	/**
	 * Constructs the QuestState
	 * @param id unique ID for the quest in the system
	 * @param state the quest's state for the player, can be either hidden, available, triggered, fulfilled, completed
	 */
	public QuestState(int id, String state)
	{
		this.questID = id;
		this.questState = state;
	}
	
	/**
	 * Returns the quest's unique ID
	 * @return questID the quest's unique ID
	 */
	public int getID()
	{
		return questID;
	}
	
	/**
	 * Returns the quest's state
	 * @return questState the state of the quest for a player
	 */
	public String getState()
	{
		return questState;
	}

	/**
	 * Assigns the quest's adventures using an ArrayList of adventures prepared already
	 * @param adventureList a list containing multiple adventures for a quest
	 */
	public void addAdventures(ArrayList<AdventureState> adventureList) 
	{
		for(AdventureState adventure : adventureList) 
		{
			this.adventureList.add(adventure);
		}
	}

	/**
	 * Returns the size of this quest's adventure list
	 * @return the number of adventures this quest has
	 */
	public int getSizeOfAdventureList() 
	{
		return this.adventureList.size();
	}

	/**
	 * Change the quest's state from hidden to available
	 * Also change all the quest's adventures from hidden
	 * to pending.
	 */
	public void trigger()
	{
		if(this.getState().equals("hidden"))
		{
			this.questState = "available";
			for(AdventureState state: adventureList)
			{
				state.trigger();
			}
		}
	}
	
	/**
	 * Returns the adventures in this quest
	 * @return list of adventures
	 */
	public ArrayList<AdventureState> getAdventureList()
	{
		return adventureList;
	}
}
