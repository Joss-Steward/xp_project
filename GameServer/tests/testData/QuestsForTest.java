package testData;

import data.GameLocation;
import data.Position;
import data.QuestCompletionActionParameter;
import data.QuestCompletionActionType;
import datasource.PlayerTableDataGateway;

/**
 * Creates adventures for the DB
 * 
 * @author merlin
 *
 */
public enum QuestsForTest
{

	/**
	 * 
	 */
	ONE_BIG_QUEST(1, "t", "Quest 1",
			new GameLocation("current.tmx", new Position(4, 14)), 5, 2,
			QuestCompletionActionType.TELEPORT, new GameLocation("current.tmx",
					new Position(3, 3))),
	/**
	 * 
	 */
	THE_OTHER_QUEST(2, "t", "Quest 2", new GameLocation("current.tmx",
			new Position(4, 14)), 4, 2, QuestCompletionActionType.NO_ACTION, null),
	/**
	 * 
	 */
	ONE_SAME_LOCATION_QUEST(3, "t", "Quest 3", new GameLocation("current.tmx",
			new Position(4, 14)), 3, 2, QuestCompletionActionType.TELEPORT,
			new GameLocation("current.tmx", new Position(3, 3))),
	/**
	 * 
	 */

	THE_LITTLE_QUEST(4, "t", "Quest 4", new GameLocation("current.tmx", new Position(2,
			32)), 5, 1, QuestCompletionActionType.TELEPORT, new GameLocation(
			"current.tmx", new Position(3, 3))),
	/**
	 * 
	 */
	CHAT_TO_AN_NPC_QUEST(5, "t", "Quest 5", new GameLocation("current.tmx", new Position(
			0, 0)), 5, 1, QuestCompletionActionType.TELEPORT, new GameLocation(
			"current.tmx", new Position(3, 3))),
	/**
	 * 
	 */
	TELEPORT_QUEST(6, "t", "Teleporting Quest", new GameLocation("current.tmx",
			new Position(2, 32)), 1, 1, QuestCompletionActionType.TELEPORT,
			new GameLocation("sortingRoom.tmx", new Position(3, 3))),

	/**
	 * The real opening quest
	 */
	ONRAMPING_QUEST(
			100,
			"Introductory Quest",
			"Welcome!  For your first quest, you need to learn a little bit about this world.  Press Q to see what you need to do.  "
					+ "Double clicking on a quest in the quest screen will show you its adventures.",
			PlayerTableDataGateway.INITIAL_GAME_LOCATION, 6, 6,
			QuestCompletionActionType.TELEPORT, new GameLocation("current.tmx",
					new Position(4, 13))),
	/**
	 * Real quest to make them explore
	 */
	EXPLORATION_QUEST(101, "Exploration", "Explore your new school", new GameLocation(
			"current.tmx", new Position(4, 13)), 2, 5,
			QuestCompletionActionType.NO_ACTION, null);

	private int questID;
	private String questTitle;
	private String questDescription;
	private GameLocation gameLocation;
	private int adventuresForFulfillment;
	private int experienceGained;
	private QuestCompletionActionType completionActionType;
	private QuestCompletionActionParameter completionActionParameter;

	/**
	 * Constructor for Quests Enum
	 * 
	 * @param questID
	 *            this quest's unique ID
	 * @param questTitle
	 *            this quest's title
	 * @param questDescription
	 *            what the player has to do
	 * @param experienceGained
	 *            the number of experience points you get when you fulfill the
	 *            quest
	 * @param adventuresForFulfillment
	 *            the number of adventures you must complete to fulfill the
	 *            quest
	 * @param completionActionType
	 *            TThe type of action that should be taken when this quest
	 *            complete
	 * @param completionActionParam
	 *            The parameter for the completion action
	 */
	QuestsForTest(int questID, String questTitle, String questDescription,
			GameLocation gameLocation, int experienceGained,
			int adventuresForFulfillment, QuestCompletionActionType completionActionType,
			QuestCompletionActionParameter completionActionParam)
	{
		this.questID = questID;
		this.questTitle = questTitle;
		this.questDescription = questDescription;
		this.gameLocation = gameLocation;
		this.experienceGained = experienceGained;
		this.adventuresForFulfillment = adventuresForFulfillment;
		this.completionActionType = completionActionType;
		this.completionActionParameter = completionActionParam;
	}

	/**
	 * @return the number of adventures you must complete to fulfill the quest
	 */
	public int getAdventuresForFulfillment()
	{
		return adventuresForFulfillment;
	}

	/**
	 * @return the number of experience points you get for fulfilling the quest
	 */
	public int getExperienceGained()
	{
		return experienceGained;
	}

	/**
	 * @return the name of the map that contains the trigger point for this
	 *         quest
	 */
	public String getMapName()
	{
		return gameLocation.getMapName();
	}

	/**
	 * @return the position of the trigger point for this quest
	 */
	public Position getPosition()
	{
		return gameLocation.getPosition();
	}

	/**
	 * @return the questDescription
	 */
	public String getQuestDescription()
	{
		return questDescription;
	}

	/**
	 * @return the questID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return the completion action type for this quest
	 */
	public QuestCompletionActionType getCompletionActionType()
	{
		return completionActionType;
	}

	/**
	 * 
	 * @return the parameter describing the details of the completion action for
	 *         this quest
	 */
	public QuestCompletionActionParameter getCompletionActionParameter()
	{
		return completionActionParameter;
	}

	/**
	 * @return this quest's title
	 */
	public String getQuestTitle()
	{
		return questTitle;
	}

}
