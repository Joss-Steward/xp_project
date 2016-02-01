package model;

import java.io.Serializable;
import java.util.ArrayList;

import datasource.QuestStateEnum;

/**
 * Player has a quest that will contain a description
 * id, state, and list of adventures.
 * @author nk3668
 *
 */
public class ClientPlayerQuest implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int questID;

	private String questDescription;
	private QuestStateEnum state;
	private ArrayList<ClientPlayerAdventure> adventures = new ArrayList<ClientPlayerAdventure>();
	private int experiencePointsGained;
	private int adventuresToFulfillment;
	/**
	 * Constructor for client player quest
	 * @param questID the quests id
	 * @param questDescription the quests description
	 * @param state the quests state
	 * @param experiencePointsGained the number of experience you get when you fulfill this quest
	 * @param adventuresToFulfillment the number of adventures required to fulfill this quest
	 */
	public ClientPlayerQuest(int questID, String questDescription, QuestStateEnum state, int experiencePointsGained, int adventuresToFulfillment)
	{
		this.questID = questID;
		this.questDescription = questDescription;
		this.state = state;
		this.experiencePointsGained = experiencePointsGained;
		this.adventuresToFulfillment = adventuresToFulfillment;
	}
	/**
	 * Add adventure to the list of ClientPlayerAdventures
	 * @param a the adventure being added
	 */
	public void addAdventure(ClientPlayerAdventure a) 
	{
		adventures.add(a);
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientPlayerQuest other = (ClientPlayerQuest) obj;
		if (adventures == null)
		{
			if (other.adventures != null)
				return false;
		} else if (!adventures.equals(other.adventures))
			return false;
		if (adventuresToFulfillment != other.adventuresToFulfillment)
			return false;
		if (experiencePointsGained != other.experiencePointsGained)
			return false;
		if (questDescription == null)
		{
			if (other.questDescription != null)
				return false;
		} else if (!questDescription.equals(other.questDescription))
			return false;
		if (questID != other.questID)
			return false;
		if (state != other.state)
			return false;
		return true;
	}

	/**
	 * Return the ClientPlayerAdventure array list
	 * @return adventures
	 */
	public ArrayList<ClientPlayerAdventure> getAdventureList() 
	{
		return adventures;
	}

	/**
	 * Getter for adventure list
	 * @return the list of the quests adventures
	 */
	public int getAdventureListSize() 
	{
		return adventures.size();
	}

	/**
	 * @return the number of adventures required to fulfill this quest
	 */
	public int getAdventuresToFulfillment()
	{
		return adventuresToFulfillment;
	}

	/**
	 * @return the number of experience points you get when you fulfill this quest
	 */
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * Getter for quest desc
	 * @return the quest description
	 */
	public String getQuestDescription() 
	{
		return questDescription;
	}

	/**
	 * Getter for quest id
	 * @return the quests id
	 */
	public int getQuestID() 
	{
		return questID;
	}

	/**
	 * Getter for quest state
	 * @return the quests state
	 */
	public QuestStateEnum getQuestState() 
	{
		return state;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adventures == null) ? 0 : adventures.hashCode());
		result = prime * result + adventuresToFulfillment;
		result = prime * result + experiencePointsGained;
		result = prime * result
				+ ((questDescription == null) ? 0 : questDescription.hashCode());
		result = prime * result + questID;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	/**
	 * Set the Client Player Adventure array list to the given array list
	 * @param adventureList ClientPlayerAdventure ArrayList
	 */
	public void setAdventures(ArrayList<ClientPlayerAdventure> adventureList) 
	{
		this.adventures = adventureList;
	}

	/**
	 * @param newState the new state of the quest
	 */
	public void setState(QuestStateEnum newState) 
	{
		this.state = newState;
	}
	
}
