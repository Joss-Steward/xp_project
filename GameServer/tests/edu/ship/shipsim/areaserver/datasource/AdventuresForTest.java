package edu.ship.shipsim.areaserver.datasource;

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
	ONE(1, "Adventure Description 1", 1),
	/**
	 * 
	 */
	TWO(2, "Adventure Description 2", 1);
	
	private int adventureID;
	private String adventureDescription;
	private int questID;
	
	/**
	 * Constructor for Adventures Enum
	 * @param adventureID this adventure's unique ID
	 * @param adventureDescription what the player has to do
	 * @param questID the ID of the quest that contains this adventure
	 */
	AdventuresForTest(int adventureID, String adventureDescription, int questID)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.questID = questID;
	}

	/**
	 * @return the adventureID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the adventureDescription
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return the questID
	 */
	public int getQuestID()
	{
		return questID;
	}
	
}
