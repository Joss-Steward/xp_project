package datasource;

import data.Position;

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
	ONE_BIG_QUEST(1, "Quest 1", "current.tmx", new Position(4, 14), 5, 2),
	/**
	 * 
	 */
	THE_OTHER_QUEST(2, "Quest 2", "sillymap.tmx", new Position(42, 2), 4, 2),
	/**
	 * 
	 */
	ONE_SAME_LOCATION_QUEST(3, "Quest 3", "current.tmx", new Position(4, 14), 3, 2),
	/**
	 * 
	 */
	THE_LITTLE_QUEST(4, "Quest 4", "current.tmx", new Position(2, 32), 5, 1);
	
	private int questID;
	private String questDescription;
	private String mapName;
	private Position position;
	private int adventuresForFulfillment;
	private int experienceGained;

	/**
	 * Constructor for Quests Enum
	 * 
	 * @param questID
	 *            this quest's unique ID
	 * @param adventureDescription
	 *            what the player has to do
	 * @param experienceGained
	 *            the number of experience points you get when you fulfill the
	 *            quest
	 * @param adventuresForFulfillment
	 *            the number of adventures you must complete to fulfill the
	 *            quest
	 */
	QuestsForTest(int questID, String adventureDescription, String mapName,
			Position position, int experienceGained, int adventuresForFulfillment)
	{
		this.questID = questID;
		this.questDescription = adventureDescription;
		this.mapName = mapName;
		this.position = position;
		this.experienceGained = experienceGained;
		this.adventuresForFulfillment = adventuresForFulfillment;
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
		return mapName;
	}

	/**
	 * @return the position of the trigger point for this quest
	 */
	public Position getPosition()
	{
		return position;
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

}
