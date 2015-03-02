package model;

import java.util.ArrayList;

import datasource.QuestStateList;

/**
 * Player has a quest that will contain a description
 * id, state, and list of adventures.
 * @author nk3668
 *
 */
public class ClientPlayerQuest {
	private int questID;
	private String questDescription;
	private QuestStateList state;
	private ArrayList<ClientPlayerAdventure> adventures;
	
	/**
	 * Constructor for client player quest
	 * @param questID the quests id
	 * @param questDescription the quests description
	 * @param state the quests state
	 */
	public ClientPlayerQuest(int questID, String questDescription, QuestStateList state){
		this.questID = questID;
		this.questDescription = questDescription;
		this.state = state;
	}

	/**
	 * Getter for quest id
	 * @return the quests id
	 */
	public int getQuestID() {
		return questID;
	}

	/**
	 * Getter for quest desc
	 * @return the quest description
	 */
	public String getQuestDescription() {
		return questDescription;
	}

	/**
	 * Getter for quest state
	 * @return the quests state
	 */
	public QuestStateList getQuestState() {
		return state;
	}

	/**
	 * Getter for adventure list
	 * @return the list of the quests adventures
	 */
	public Object getAdventureListSize() {
		return adventures.size();
	}
	
}
