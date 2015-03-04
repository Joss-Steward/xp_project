package edu.ship.shipsim.areaserver.datasource;

import datasource.QuestStateEnum;

/**
 * Creates adventures for the DB
 * 
 * @author merlin
 *
 */
public enum QuestStatesForTest
{
	/**
	 * 
	 */
	PLAYER1_QUEST1(1, 1, QuestStateEnum.AVAILABLE),
	/**
	 * 
	 */
	PLAYER1_QUEST2(1, 2, QuestStateEnum.FULFILLED),
	/**
	 * 
	 */
	PLAYER2_Quest1(2, 1, QuestStateEnum.TRIGGERED);

	private int questID;
	private int playerID;
	private QuestStateEnum questState;

	/**
	 * Constructor for Quest State Enum
	 * 
	 * @param questID
	 *            a quest's unique ID
	 * @param playerID
	 *            a player's unique ID
	 * @param questState
	 *            the state of the quest for the specified player
	 */
	QuestStatesForTest(int playerID, int questID, QuestStateEnum questState)
	{
		this.questID = questID;
		this.playerID = playerID;
		this.questState = questState;
	}

	/**
	 * @return the player's unique ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the state of the quest for the given player
	 */
	public QuestStateEnum getState()
	{
		return questState;
	}

	/**
	 * @return the questID
	 */
	public int getQuestID()
	{
		return questID;
	}

}
