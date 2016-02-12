package testData;

import data.AdventureCompletionCriteria;
import data.AdventureCompletionType;
import data.CriteriaString;
import data.MapLocation;
import data.Position;

/**
 * Creates adventures for the DB
 * @author merlin
 *
 */
public enum AdventuresForTest
{
	/**
	 * 
	 */
	QUEST1_ADVENTURE_1(1, "Quest 1: Adventure Description 1", 1, 1, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST1_ADVENTURE2(2, "Quest 1: Adventure Description 2", 1, 2, AdventureCompletionType.CHAT, new CriteriaString("QuizBot")),
	/**
	 * 
	 */
	QUEST2_ADVENTURE1(1, "Quest 2: Adventure Description 1", 2, 3, AdventureCompletionType.EXTERNAL, new CriteriaString("Department Secretary")),
	/**
	 * 
	 */
	QUEST2_ADVENTURE2(2, "Quest 2: Adventure Description 2", 2, 4, AdventureCompletionType.MOVEMENT, new MapLocation("current.tmx",new Position(42,3))),
	/**
	 * 
	 */
	QUEST2_ADVENTURE3(3, "Quest 2: Adventure Description 3", 2, 3, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST2_ADVENTURE4(4, "Quest 2: Adventure Description 4", 2, 33, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST3_ADVENTURE1(1, "Adventure 1 for Quest 3", 3, 5, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST3_ADVENTURE2(2, "Adventure 2 for Quest 3", 3, 5, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST3_ADVENTURE3(3, "Adventure 3 for Quest 3", 3, 5, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST1_ADVENTURE3(3, "One more Adventure", 1, 3, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST4_ADVENTURE1(1, "Quest 4 Adventure", 4, 5, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST4_ADVENTURE2(2, "Quest 4 Adventure 2", 4, 5, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST4_ADVENTURE3(3, "Quest 4 Adventure 3", 4, 5, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor")),
	/**
	 * 
	 */
	QUEST4_ADVENTURE4(4, "Quest 4 Adventure 4", 4, 5, AdventureCompletionType.EXTERNAL, new CriteriaString("Lab Instructor"));
	
	
	private int adventureID;
	private String adventureDescription;
	private int questID;
	private int experiencePointsGained;
	private AdventureCompletionType completionType;
	private AdventureCompletionCriteria completionCriteria;
	
	/**
	 * Constructor for Adventures Enum
	 * @param adventureID this adventure's unique ID
	 * @param adventureDescription what the player has to do
	 * @param questID the ID of the quest that contains this adventure
	 * @param experiencePointsGained the number of experience points the player gets when he completes the adventure
	 * @param completionType the method the player must use to complete this adventure
	 * @param signatureSpecification the rules about who can sign for an outside adventure
	 */
	AdventuresForTest(int adventureID, String adventureDescription, int questID, int experiencePointsGained, AdventureCompletionType completionType, AdventureCompletionCriteria signatureSpecification)
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
