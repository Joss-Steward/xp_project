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
	 * Getter for quest id
	 * @return the quests id
	 */
	public int getQuestID() 
	{
		return questID;
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
	 * Getter for quest state
	 * @return the quests state
	 */
	public QuestStateEnum getQuestState() 
	{
		return state;
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
	 * Add adventure to the list of ClientPlayerAdventures
	 * @param a the adventure being added
	 */
	public void addAdventure(ClientPlayerAdventure a) 
	{
		adventures.add(a);
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
	 * Set the Client Player Adventure array list to the given array list
	 * @param adventureList ClientPlayerAdventure ArrayList
	 */
	public void setAdventures(ArrayList<ClientPlayerAdventure> adventureList) 
	{
		this.adventures = adventureList;
	}

	/**
	 * @return the number of experience points you get when you fulfill this quest
	 */
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @return the number of adventures required to fulfill this quest
	 */
	public int getAdventuresToFulfillment()
	{
		return adventuresToFulfillment;
	}

	/**
	 * @param newState the new state of the quest
	 */
	public void setState(QuestStateEnum newState) 
	{
		this.state = newState;
	}
	
}
