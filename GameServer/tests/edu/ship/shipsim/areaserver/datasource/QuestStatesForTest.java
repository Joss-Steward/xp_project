package edu.ship.shipsim.areaserver.datasource;

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
	PLAYER1_QUEST1(1, 1, QuestState.AVAILABLE),
	/**
	 * 
	 */
	PLAYER1_QUEST2(1, 2, QuestState.FULFILLED),
	/**
	 * 
	 */
	PLAYER2_Quest1(2, 1, QuestState.TRIGGERED);

	private int questID;
	private int playerID;
	private QuestState questState;

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
	QuestStatesForTest(int playerID, int questID, QuestState questState)
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
	public QuestState getQuestState()
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
