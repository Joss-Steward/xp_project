package model;

import data.AdventureCompletionCriteria;
import data.AdventureCompletionType;

/**
 * Data Transfer Object for the Adventure Data Gateway to deliver records.
 * 
 * @author merlin
 *
 */
public final class AdventureRecord
{
	private final int adventureID;
	private final String adventureDescription;
	private final int questID;
	private final int experiencePointsGained;
	private final AdventureCompletionCriteria completionCriteria;
	private AdventureCompletionType completionType;

	/**
	 * Create it
	 * 
	 * @param questID the unique ID of the quest that contains the adventure
	 * @param adventureID the adventure's unique ID
	 * @param adventureDescription the adventure's description
	 * @param experiencePointsGained the number of points earned by completing
	 *            this adventure
	 * @param completionType the type of action the player must do to complete
	 *            this adventure
	 * @param completionCriteria the criteria for satisfying this adventure
	 */
	public AdventureRecord(int questID, int adventureID, String adventureDescription, int experiencePointsGained,
			AdventureCompletionType completionType, AdventureCompletionCriteria completionCriteria)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.questID = questID;
		this.experiencePointsGained = experiencePointsGained;
		this.completionType = completionType;
		this.completionCriteria = completionCriteria;
	}

	/**
	 * @return the type of action the player must do to complete this adventure
	 */
	public AdventureCompletionType getCompletionType()
	{
		return completionType;
	}

	/**
	 * retrieve the adventure's ID
	 * 
	 * @return adventureID the adventure's unique ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * retrieve the adventures description
	 * 
	 * @return adventureDescription
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * retrieve the quest's ID
	 * 
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
		return "Quest " + questID + ":     " + "Adventure  " + adventureID + "      " + adventureDescription;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adventureDescription == null) ? 0 : adventureDescription.hashCode());
		result = prime * result + adventureID;
		result = prime * result + experiencePointsGained;
		result = prime * result + questID;
		result = prime * result + ((completionCriteria == null) ? 0 : completionCriteria.hashCode());
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
		if (experiencePointsGained != other.experiencePointsGained)
			return false;
		if (questID != other.questID)
			return false;
		if (completionCriteria == null)
		{
			if (other.completionCriteria != null)
				return false;
		} else if (!completionCriteria.equals(other.completionCriteria))
			return false;
		return true;
	}

	/**
	 * @return the number of points you get when you complete this adventure
	 */
	public int getExperiencePointsGained()
	{
		return experiencePointsGained;
	}

	/**
	 * @return the criteria for completing this adventure
	 */
	public AdventureCompletionCriteria getCompletionCriteria()
	{
		return completionCriteria;
	}

	/**
	 * @return true if the adventure must be completed in real life
	 */
	public boolean isRealLifeAdventure()
	{
		return completionType == AdventureCompletionType.REAL_LIFE;
	}
}
