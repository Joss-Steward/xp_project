package edu.ship.shipsim.areaserver.datasource;

/**
 * Data Transfer Object for the Adventure Data Gateway to deliver records.
 * @author merlin
 *
 */
public final class AdventureRecord
{
	private final int adventureID;
	private final String adventureDescription;
	private final int questID;
	
	/**
	 * Create it
	 * @param questID the unique ID of the quest that contains the adventure
	 * @param adventureID the adventure's unique ID
	 * @param adventureDescription the adventure's description
	 */
	public AdventureRecord(int questID, int adventureID, String adventureDescription)
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
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return questID + ":" + adventureID + " " + adventureDescription;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adventureDescription == null) ? 0 : adventureDescription.hashCode());
		result = prime * result + adventureID;
		result = prime * result + questID;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdventureRecord other = (AdventureRecord) obj;
		if (adventureDescription == null)
		{
			if (other.adventureDescription != null)
				return false;
		} else if (!adventureDescription.equals(other.adventureDescription))
			return false;
		if (adventureID != other.adventureID)
			return false;
		if (questID != other.questID)
			return false;
		return true;
	}
}
