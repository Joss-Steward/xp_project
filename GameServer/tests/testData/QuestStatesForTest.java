package testData;

import datatypes.QuestStateEnum;

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
	PLAYER1_QUEST1(1, 1, QuestStateEnum.AVAILABLE, false),
	/**
	 * 
	 */
	PLAYER1_QUEST2(1, 2, QuestStateEnum.FULFILLED, true),
	/**
	 * 
	 */
	PLAYER1_QUEST3(1, 3, QuestStateEnum.AVAILABLE, true),

	/**
	 * 
	 */
	PLAYER5_QUEST4(5, 4, QuestStateEnum.AVAILABLE, true),

	/**
	 * 
	 */
	PLAYER2_QUEST1(2, 1, QuestStateEnum.TRIGGERED, true),
	/**
	 * 
	 */
	PLAYER2_QUEST2(2, 2, QuestStateEnum.AVAILABLE, true),
	/**
	 * 
	 */
	PLAYER2_QUEST4(2, 4, QuestStateEnum.FULFILLED, true),
	/**
	 * 
	 */
	PLAYER4_QUEST3(4, 3, QuestStateEnum.TRIGGERED, true),
	/**
	 * 
	 */
	PLAYER4_QUEST4(4, 4, QuestStateEnum.AVAILABLE, true),
	/**
	 * 
	 */
	PLAYER7_QUEST1(7, 1, QuestStateEnum.TRIGGERED, false),
	/**
	 * A quest that is ready to be triggered (with no states for the included
	 * adventures)
	 */
	PLAYER7_QUEST2(7, 2, QuestStateEnum.AVAILABLE, false),
	/**
	 * 
	 */
	MARTY_QUEST5(PlayersForTest.MARTY.getPlayerID(), 5, QuestStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	MARTY_QUEST11(PlayersForTest.MARTY.getPlayerID(), 11, QuestStateEnum.EXPIRED, false),
	/**
	 * 
	 */
	PLAYER8_QUEST2(8, 2, QuestStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	PLAYER8_QUEST10(8, 10, QuestStateEnum.TRIGGERED, true),
	/**
	 * 
	 */
	PLAYER19_QUEST6(19, 6, QuestStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	PLAYER19_QUEST7(19, 7, QuestStateEnum.AVAILABLE, false),
	/**
	 * 
	 */
	PLAYER19_QUEST8(19, 8, QuestStateEnum.EXPIRED, false),
	/**
	 *
	 */
	PLAYER19_QUEST9(19, 9, QuestStateEnum.COMPLETED, false),
	/**
	 * Newbie should have the first quest already triggered
	 */

	NEWBIE_ONRAMPING(PlayersForTest.NEWBIE.getPlayerID(), QuestsForTest.ONRAMPING_QUEST.getQuestID(), QuestStateEnum.TRIGGERED, true);

	private int playerID;
	private int questID;
	private QuestStateEnum questState;
	private boolean needingNotification;

	/**
	 * Constructor for Quest State Enum
	 * 
	 * @param playerID a player's unique ID
	 * @param questID a quest's unique ID
	 * @param questState the state of the quest for the specified player
	 * @param needingNotification true if this player should be notified about
	 *            the state of this quest
	 */
	QuestStatesForTest(int playerID, int questID, QuestStateEnum questState, boolean needingNotification)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.questState = questState;
		this.needingNotification = needingNotification;
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
		return needingNotification;
	}

}
