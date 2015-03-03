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
		QuestStateRecord other = (QuestStateRecord) obj;
		if (playerID != other.playerID)
			return false;
		if (questID != other.questID)
			return false;
		if (state != other.state)
			return false;
		return true;
	}

	/**
	 * @return the player's ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the quest ID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return the player's state for this quest
	 */
	public QuestStateList getState()
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
		result = prime * result + playerID;
		result = prime * result + questID;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

}
