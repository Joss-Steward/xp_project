package testData;

import data.AdventureStateEnum;

/**
 * Creates adventures for the DB
 * 
 * @author merlin
 *
 */
public enum AdventureStatesForTest
{
	/**
	 * 
	 */
	PLAYER1_QUEST1_ADV1(1, 1, 1, AdventureStateEnum.COMPLETED, true),
	/**
	 * 
	 */
	PLAYER1_QUEST1_ADV2(1, 1, 2, AdventureStateEnum.COMPLETED, false),
	/**
	 * 
	 */
	PLAYER1_QUEST2_ADV1(1, 2, 1, AdventureStateEnum.COMPLETED, false),
	/**
	 * 
	 */
	PLAYER1_QUEST2_ADV2(1, 2, 2, AdventureStateEnum.COMPLETED, false),
	/**
	 * 
	 */
	PLAYER1_QUEST2_ADV3(1, 2, 3, AdventureStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	PLAYER1_QUEST3_ADV1(1, 3, 1, AdventureStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	PLAYER1_QUEST3_ADV2(1, 3, 2, AdventureStateEnum.COMPLETED, false),
	/**
	 * 
	 */
	PLAYER1_QUEST3_ADV3(1, 3, 3, AdventureStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	PLAYER2_QUEST1_ADV1(2, 1, 1, AdventureStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	PLAYER2_QUEST1_ADV2(2, 1, 2, AdventureStateEnum.TRIGGERED, false),

	/**
	 * 
	 */
	PLAYER2_QUEST1_ADV3(2, 1, 3, AdventureStateEnum.TRIGGERED, false),

	/**
	 * 
	 */
	PLAYER2_QUEST4_ADV1(2, 4, 1, AdventureStateEnum.COMPLETED, false),

	/**
	 * 
	 */
	PLAYER2_QUEST4_ADV2(2, 4, 2, AdventureStateEnum.TRIGGERED, false),

	/**
	 * 
	 */
	PLAYER2_QUEST4_ADV3(2, 4, 3, AdventureStateEnum.COMPLETED, false),

	/**
	 * 
	 */
	PLAYER2_QUEST4_ADV4(2, 4, 4, AdventureStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	PLAYER3_QUEST1_ADV1(3, 1, 1, AdventureStateEnum.COMPLETED, false),
	/**
	 *
	 */
	PLAYER4_QUEST3_ADV1(4, 3, 1, AdventureStateEnum.COMPLETED, false),
	/**
	 * 
	 */
	PLAYER4_QUEST3_ADV2(4, 3, 2, AdventureStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	PLAYER4_QUEST3_ADV3(4, 3, 3, AdventureStateEnum.COMPLETED, false),

	/**
	 * 
	 */
	PLAYER7_QUEST1_ADV1(7, 1, 1, AdventureStateEnum.TRIGGERED, false),
	/**
	 * 
	 */
	PLAYER7_QUEST1_ADV2(7, 1, 2, AdventureStateEnum.COMPLETED, false),
	/**
	 * 
	 */
	PLAYER7_QUEST2_ADV1(7, 2, 1, AdventureStateEnum.HIDDEN, false),

	// ----------------------------------------------------------------------------------------//
	// NEWBIE's onramping adventures //
	// ----------------------------------------------------------------------------------------//

	/**
	 * 
	 */
	NEWBIE_ONRAMPING_Q(PlayersForTest.NEWBIE.getPlayerID(),
			AdventuresForTest.ONRAMPING_PRESS_Q.getQuestID(),
			AdventuresForTest.ONRAMPING_PRESS_Q.getAdventureID(),
			AdventureStateEnum.TRIGGERED, false),

	/**
	 * 
	 */
	NEWBIE_ONRAMPING_MOVE_UP(PlayersForTest.NEWBIE.getPlayerID(),
			AdventuresForTest.ONRAMPING_MOVE_FORWARD.getQuestID(),
			AdventuresForTest.ONRAMPING_MOVE_FORWARD.getAdventureID(),
			AdventureStateEnum.TRIGGERED, false),

	/**
	 * 
	 */
	NEWBIE_ONRAMPING_MOVE_DOWN(PlayersForTest.NEWBIE.getPlayerID(),
			AdventuresForTest.ONRAMPING_MOVE_BACKWARD.getQuestID(),
			AdventuresForTest.ONRAMPING_MOVE_BACKWARD.getAdventureID(),
			AdventureStateEnum.TRIGGERED, false),

	/**
	 * 
	 */
	NEWBIE_ONRAMPING_MOVE_RIGHT(PlayersForTest.NEWBIE.getPlayerID(),
			AdventuresForTest.ONRAMPING_MOVE_RIGHT.getQuestID(),
			AdventuresForTest.ONRAMPING_MOVE_RIGHT.getAdventureID(),
			AdventureStateEnum.TRIGGERED, false),

	/**
	 * 
	 */
	NEWBIE_ONRAMPING_MOVE_LEFT(PlayersForTest.NEWBIE.getPlayerID(),
			AdventuresForTest.ONRAMPING_MOVE_LEFT.getQuestID(),
			AdventuresForTest.ONRAMPING_MOVE_LEFT.getAdventureID(),
			AdventureStateEnum.TRIGGERED, false);

	private int adventureID;
	private int questID;
	private int playerID;
	private AdventureStateEnum state;
	private boolean needsNotification;

	/**
	 * Constructor for Adventures Enum
	 * 
	 * @param adventureID
	 *            this adventure's unique ID
	 * @param questID
	 *            the ID of the quest that contains this adventure
	 */
	AdventureStatesForTest(int playerID, int questID, int adventureID,
			AdventureStateEnum state, boolean needsNotification)
	{
		this.adventureID = adventureID;
		this.questID = questID;
		this.playerID = playerID;
		this.state = state;
		this.needsNotification = needsNotification;
	}

	/**
	 * @return true if the state should be shared with the player
	 */
	public boolean isNeedingNotification()
	{
		return needsNotification;
	}

	/**
	 * @return the adventureID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * 
	 * @return the player's ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * 
	 * @return the current state of the adventure for that player
	 */
	public AdventureStateEnum getState()
	{
		return state;
	}

	/**
	 * @return the questID
	 */
	public int getQuestID()
	{
		return questID;
	}

}
