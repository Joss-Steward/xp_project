package edu.ship.shipsim.areaserver.datasource;

/**
 * Data Transfer Object for the Adventure Data Gateway to deliver records.
 * @author merlin
 *
 */
public class AdventureRecord
{
	private int adventureID;
	private String adventureDescription;
	private int questID;
	
	/**
	 * Create it
	 * @param adventureID the adventure's unique ID
	 * @param adventureDescription the adventure's description
	 * @param questID the unique ID of the quest that contains the adventure
	 */
	public AdventureRecord(int adventureID, String adventureDescription, int questID)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.questID = questID;
		
	}

	/**
	 * retrieve the adventure's ID
	 * @return adventureID the adventure's unique ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * retrieve the adventures description
	 * @return adventureDescription
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * retrieve the quest's ID
	 * @return questID the unique ID for the adventure's quest
	 */
	public int getQuestID()
	{
		return questID;
	}
}
