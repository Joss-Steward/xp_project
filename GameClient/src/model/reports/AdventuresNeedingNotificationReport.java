package model.reports;

import model.QualifiedObservableReport;

/**
 * This class will send a report that contains the strings of adventures that are 
 * currently of state "needing notification" so that the client
 * can be informed of their completion.
 * 
 * @author nk3668 & ew4344
 */
public final class AdventuresNeedingNotificationReport implements QualifiedObservableReport
{

	private final int questID;
	private final int adventureID;
	private final int playerID;
	private final String adventureDescription;
	
	/**
	 * Constructor
	 * @param playerID id of the player
	 * @param questID id of the quest
	 * @param adventureID id of the adventure
	 * @param adventureDescription the description of the adventure
	 */
	public AdventuresNeedingNotificationReport(int playerID, int questID, int adventureID, String adventureDescription) 
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
	}

	/**
	 * @return id of the quest
	 */
	public int getQuestID() 
	{
		return questID;
	}

	/**
	 * @return id of the adventure
	 */
	public int getAdventureID() 
	{
		return adventureID;
	}

	/**
	 * @return id of the player
	 */
	public int getPlayerID() 
	{
		return playerID;
	}
	
	/**
	 * @return description of adventure
	 */
	public String getAdventureDescription() 
	{
		return adventureDescription;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((adventureDescription == null) ? 0 : adventureDescription
						.hashCode());
		result = prime * result + adventureID;
		result = prime * result + playerID;
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
		AdventuresNeedingNotificationReport other = (AdventuresNeedingNotificationReport) obj;
		if (adventureDescription == null) {
			if (other.adventureDescription != null)
				return false;
		} else if (!adventureDescription.equals(other.adventureDescription))
			return false;
		if (adventureID != other.adventureID)
			return false;
		if (playerID != other.playerID)
			return false;
		if (questID != other.questID)
			return false;
		return true;
	}
}
