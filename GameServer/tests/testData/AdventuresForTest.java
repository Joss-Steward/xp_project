package testData;

import data.AdventureCompletionCriteria;
import data.AdventureCompletionType;
import data.CriteriaString;
import data.GameLocation;
import data.CriteriaInteger;
import datatypes.Position;

/**
 * Creates adventures for the DB
 * 
 * @author merlin
 *
 */
public enum AdventuresForTest
{
	/**
	 * 
	 */
	QUEST1_ADVENTURE_1(1, "Quest 1: Adventure 1: MOVE!!!!", 1, 1, AdventureCompletionType.MOVEMENT, new GameLocation("current.tmx", new Position(PlayersForTest.MERLIN.getPosition().getRow() + 1, PlayersForTest.MERLIN.getPosition().getColumn()))),
	/**
	 * 
	 */
	QUEST1_ADVENTURE2(2, "Quest 1: Adventure Description 2", 1, 2, AdventureCompletionType.CHAT, new CriteriaString("QuizBot")),
	/**
	 * 
	 */
	QUEST2_ADVENTURE1(1, "Quest 2: Adventure Description 1", 2, 3, AdventureCompletionType.REAL_LIFE, new CriteriaString("Department Secretary")),
	/**
	 * 
	 */
	QUEST2_ADVENTURE2(2, "Quest 2: Adventure Description 2", 2, 4, AdventureCompletionType.MOVEMENT, new GameLocation("current.tmx", new Position(4, 15))),
	/**
	 * 
	 */
	QUEST2_ADVENTURE3(3, "Quest 2: Adventure Description 3", 2, 3, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST2_ADVENTURE4(4, "Quest 2: Adventure Description 4", 2, 33, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST3_ADVENTURE1(1, "Adventure 1 for Quest 3", 3, 5, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST3_ADVENTURE2(2, "Adventure 2 for Quest 3", 3, 5, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST3_ADVENTURE3(3, "Adventure 3 for Quest 3", 3, 5, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST1_ADVENTURE3(3, "One more Adventure", 1, 3, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST4_ADVENTURE1(1, "Quest 4 Adventure", 4, 5, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST4_ADVENTURE2(2, "Quest 4 Adventure 2", 4, 5, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST4_ADVENTURE3(3, "Quest 4 Adventure 3", 4, 5, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST4_ADVENTURE4(4, "Quest 4 Adventure 4", 4, 5, AdventureCompletionType.REAL_LIFE, new CriteriaString("Lab Instructor")),

	/**
	 * 
	 */
	QUEST5_ADVENTURE1(1, "Quest 5 Adventure 1", 5, 1, AdventureCompletionType.CHAT, new CriteriaString("QuizBot")),
	/**
	 * 
	 */
	QUEST6_ADVENTURE_1(1, "Quest 6: Adventure 1: MOVE!!!!", 6, 1, AdventureCompletionType.MOVEMENT, new GameLocation("current.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 1, PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 * 
	 */
	QUEST6_ADVENTURE_2(2, "Quest 6: Adventure 2: MOVE!!!!", 6, 2, AdventureCompletionType.MOVEMENT, new GameLocation("current.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 2, PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 * 
	 */
	QUEST7_ADVENTURE_1(1, "Quest 7: Adventure 1: MOVE!!!!", 7, 1, AdventureCompletionType.MOVEMENT, new GameLocation("current.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 1, PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	* 
	*/
	QUEST7_ADVENTURE_2(2, "Quest 7: Adventure 2: MOVE!!!!", 7, 0, AdventureCompletionType.MOVEMENT, new GameLocation("current.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 2, PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 * 
	 */
	QUEST8_ADVENTURE_1(1, "Quest 8: Adventure 1: I'm dead", 8, 0, AdventureCompletionType.MOVEMENT, new GameLocation("current.tmx", new Position(PlayersForTest.HERSH.getPosition().getRow() + 3, PlayersForTest.HERSH.getPosition().getColumn()))),
	/**
	 * 
	 */
	QUEST8_ADVENTURE_2(2, "Quest 8: Adventure 2: Get points!", 8, 1, AdventureCompletionType.KNOWLEDGE_POINTS, new CriteriaInteger(5)),
	/**
	 * 
	 */
	QUEST8_ADVENTURE_3(3, "Quest 8: Adventure 3: Get points!", 8, 1, AdventureCompletionType.KNOWLEDGE_POINTS, new CriteriaInteger(5)),
	/**
	 * 
	 */
	QUEST8_ADVENTURE_4(4, "Quest 8: Adventure 4: Get points!", 8, 1, AdventureCompletionType.KNOWLEDGE_POINTS, new CriteriaInteger(5)),
	/**
	 * 
	 */
	QUEST10_ADVENTURE_1(1, "Quest 10: Adventure 2: Get points!", QuestsForTest.KNOWLEDGE_QUEST.getQuestID(), 1, AdventureCompletionType.KNOWLEDGE_POINTS, new CriteriaInteger(5)),
	/**
	 * 
	 */
	QUEST11_ADVENTURE_1(1, "Quest 11: Adventure 1: Expired Quest!", QuestsForTest.TRIGGERED_STATE_TO_EXPIRED_STATE_QUEST.getQuestID(), 2, AdventureCompletionType.KEYSTROKE, new CriteriaString("e")),

	/****************************************************/
	/* Onramping Adventures */
	/****************************************************/

	/**
	 * 
	 */
	ONRAMPING_PRESS_Q(1, "Press the Q key to see your active quests and adventures", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2, AdventureCompletionType.KEYSTROKE, new CriteriaString("q")),
	/**
	 * 
	 */
	ONRAMPING_MOVE_FORWARD(2, "Press up arrow to move forward", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2, AdventureCompletionType.KEYSTROKE, new CriteriaString("\u2191")),
	/**
	 * 
	 */
	ONRAMPING_MOVE_BACKWARD(3, "Press down arrow to move backward", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2, AdventureCompletionType.KEYSTROKE, new CriteriaString("\u2193")),
	/**
	 * 
	 */
	ONRAMPING_MOVE_LEFT(4, "Press left arrow to move left", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2, AdventureCompletionType.KEYSTROKE, new CriteriaString("\u2190")),
	/**
	 * 
	 */
	ONRAMPING_MOVE_RIGHT(5, "Press right arrow to move right", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2, AdventureCompletionType.KEYSTROKE, new CriteriaString("\u2192")),

	/**
	 * 
	 */
	ONRAMPING_CHAT_WITH_SORTING_HAT(6, "Use the chat window to say something to the Red Hat", QuestsForTest.ONRAMPING_QUEST.getQuestID(), 2, AdventureCompletionType.CHAT, new CriteriaString("Red Hat")),

	/**
	 * 
	 */
	EXPLORING_FIND_QUIZNASIUM(1, "Find the Quiznasium", QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2, AdventureCompletionType.MOVEMENT, new GameLocation("quiznasium.tmx", new Position(4, 23))),
	/**
	 * 
	 */
	EXPLORING_FIND_STUDY_HALL(2, "Find the Study Hall", QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2, AdventureCompletionType.MOVEMENT, new GameLocation("homework.tmx", new Position(48, 24))),
	/**
	 * 
	 */
	EXPLORING_FIND_SECRET_ROOM(3, "Find the Secret Room!", QuestsForTest.EXPLORATION_QUEST.getQuestID(), 2, AdventureCompletionType.MOVEMENT, new GameLocation("Quiznasium.tmx", new Position(13, 22)));

	private int adventureID;
	private String adventureDescription;
	private int questID;
	private int experiencePointsGained;
	private AdventureCompletionType completionType;
	private AdventureCompletionCriteria completionCriteria;

	/**
	 * Constructor for Adventures Enum
	 * 
	 * @param adventureID this adventure's unique ID
	 * @param adventureDescription what the player has to do
	 * @param questID the ID of the quest that contains this adventure
	 * @param experiencePointsGained the number of experience points the player
	 *            gets when he completes the adventure
	 * @param completionType the method the player must use to complete this
	 *            adventure
	 * @param signatureSpecification the rules about who can sign for an outside
	 *            adventure
	 */
	AdventuresForTest(int adventureID, String adventureDescription, int questID, int experiencePointsGained,
			AdventureCompletionType completionType, AdventureCompletionCriteria signatureSpecification)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.questID = questID;
		this.experiencePointsGained = experiencePointsGained;
		this.completionType = completionType;
		this.completionCriteria = signatureSpecification;
	}

	/**
	 * @return the adventureDescription
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return the adventureID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the number of points you get for completing the adventure
	 */
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @return the questID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return the rules about who confirms completion of an outside quest
	 */
	public AdventureCompletionCriteria getCompletionCriteria()
	{
		return completionCriteria;
	}

	/**
	 * @return the type of action the player uses to complete this adventure
	 */
	public AdventureCompletionType getCompletionType()
	{
		return completionType;
	}

}
