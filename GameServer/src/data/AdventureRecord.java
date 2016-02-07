package data;

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
	private final String signatureSpecification;

	/**
	 * Create it
	 * 
	 * @param questID
	 *            the unique ID of the quest that contains the adventure
	 * @param adventureID
	 *            the adventure's unique ID
	 * @param adventureDescription
	 *            the adventure's description
	 * @param experiencePointsGained
	 *            the number of points earned by completing this adventure
	 * @param signatureSpecification
	 *            the rules about who can sign for the adventure if it is an outside adventure
	 */
	public AdventureRecord(int questID, int adventureID, String adventureDescription,
			int experiencePointsGained, String signatureSpecification)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.questID = questID;
		this.experiencePointsGained = experiencePointsGained;
		this.signatureSpecification = signatureSpecification;
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
		return "Quest " + questID + ":     " + "Adventure  " + adventureID + "      "
				+ adventureDescription;
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
		result = prime * result + experiencePointsGained;
		result = prime * result + questID;
		result = prime
				* result
				+ ((signatureSpecification == null) ? 0 : signatureSpecification
						.hashCode());
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
		if (signatureSpecification == null)
		{
			if (other.signatureSpecification != null)
				return false;
		} else if (!signatureSpecification.equals(other.signatureSpecification))
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
	 * @return the rules about who has to sign for this if it is an outside
	 *         adventure
	 */
	public String getSignatureSpecification()
	{
		return signatureSpecification;
	}
}
