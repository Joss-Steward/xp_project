package edu.ship.shipsim.areaserver.datasource;

import data.Position;

/**
 * Creates adventures for the DB
 * @author merlin
 *
 */
public enum QuestsForTest
{
	/**
	 * 
	 */
	ONE_BIG_QUEST(1, "A Big Quest","current.tmx", new Position(4,3)),
	/**
	 * 
	 */
	THE_OTHER_QUEST(2, "The Other Quest","sillymap.tmx", new Position(42,2)),
	/**
	 * 
	 */
	ONE_SAME_LOCATION_QUEST(3, "Another Quest","current.tmx", new Position(4,3));
	
	private int questID;
	private String questDescription;
	private String mapName;
	private Position position;
	
	/**
	 * Constructor for Quests Enum
	 * @param questID this quest's unique ID
	 * @param adventureDescription what the player has to do
	 */
	QuestsForTest(int questID, String adventureDescription, String mapName, Position position)
	{
		this.questDescription = adventureDescription;
		this.questID = questID;
		this.mapName = mapName;
		this.position = position;
	}

	/**
	 * @return the name of the map that contains the trigger point for this quest
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
