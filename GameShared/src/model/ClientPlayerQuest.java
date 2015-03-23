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
	
	/**
	 * Constructor for client player quest
	 * @param questID the quests id
	 * @param questDescription the quests description
	 * @param state the quests state
	 */
	public ClientPlayerQuest(int questID, String questDescription, QuestStateEnum state)
	{
		this.questID = questID;
		this.questDescription = questDescription;
		this.state = state;
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
	
}
