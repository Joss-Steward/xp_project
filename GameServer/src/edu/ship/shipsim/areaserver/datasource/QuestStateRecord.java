package edu.ship.shipsim.areaserver.datasource;

import datasource.QuestStateList;

/**
 * A data transfer object that contains the state of a quest for a player
 * @author Merlin
 *
 */
public class QuestStateRecord
{

	private int playerID;
	private int questID;
	private QuestStateList state;

	/**
	 * @param playerID the player's unique ID
	 * @param questID the quest's unique ID
	 * @param state this player's state for the given quest
	 */
	public QuestStateRecord(int playerID, int questID, QuestStateList state)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.state = state;
	}

	/**
	 * @return the player's ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the player's state for this quest
	 */
	public QuestStateList getState()
	{
		return state;
	}

	/**
	 * @return the quest ID
	 */
	public int getQuestID()
	{
		return questID;
	}

}
