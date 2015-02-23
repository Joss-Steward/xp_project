package edu.ship.shipsim.areaserver.datasource;

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
	ONE_BIG_QUEST(1, "A Big Quest"),
	/**
	 * 
	 */
	THE_OTHER_QUEST(2, "The Other Quest");
	
	private int questID;
	private String questDescription;
	
	/**
	 * Constructor for Quests Enum
	 * @param questID this quest's unique ID
	 * @param adventureDescription what the player has to do
	 */
	QuestsForTest(int questID, String adventureDescription)
	{
		this.questDescription = adventureDescription;
		this.questID = questID;
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
