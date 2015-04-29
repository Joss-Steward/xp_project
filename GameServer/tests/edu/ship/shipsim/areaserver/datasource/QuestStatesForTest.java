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
	PLAYER1_QUEST1(1, 1, QuestStateEnum.FINISHED, false),
	/**
	 * 
	 */
	PLAYER1_QUEST2(1, 2, QuestStateEnum.FULFILLED, true),
	/**
	 * 
	 */
	PLAYER2_QUEST1(2, 1, QuestStateEnum.AVAILABLE, true),
	/**
	 * 
	 */
	PLAYER1_QUEST3(1, 3, QuestStateEnum.TRIGGERED, true),
	/**
	 * 
	 */
	PLAYER2_QUEST2(2, 4, QuestStateEnum.FULFILLED, true);
	
	private int playerID;
	private int questID;
	private QuestStateEnum questState;

	/**
	 * Constructor for Quest State Enum
	 * @param playerID
	 *            a player's unique ID
	 * @param questID
	 *            a quest's unique ID
	 * @param questState
	 *            the state of the quest for the specified player
	 * @param needingNotification true if this player should be notified about the state of this quest
	 */
	QuestStatesForTest(int playerID, int questID, QuestStateEnum questState, boolean needingNotification)
	{
		this.playerID = playerID;
		this.questID = questID;
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

	/**
	 * @return true if the player should be notified of this state
	 */
	public boolean isNeedingNotification()
	{
		return true;
	}

}
