package edu.ship.shipsim.areaserver.model.reports;

import model.QualifiedObservableReport;

/**
 * The ExperienceChangedReport class
 * @author Olivia
 * @author LaVonne
 */
public final class ExperienceChangedReport  implements QualifiedObservableReport
{

	private final int experiencePoints;
	
	private final int playerID;

	/**
	 * @param experiencePoints experience points of the player
	 * @param playerID id of the player
	 */
	public ExperienceChangedReport(int experiencePoints, int playerID) 
	{
		this.experiencePoints = experiencePoints;
		this.playerID = playerID;
	}

	/**
	 * Gets player's current experience points
	 * @return experiencePoints
	 */
	public int getExperiencePoints() 
	{
		return experiencePoints;
	}

	/**
	 * Gets player's ID
	 * @return playerID
	 */
	public int getPlayerID() 
	{
		return playerID;
	}
	
	/**
	 * Auto generated hash code
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + experiencePoints;
		result = prime * result + playerID;
		return result;
	}

	/**
	 * Auto generated equals method
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
		ExperienceChangedReport other = (ExperienceChangedReport) obj;
		if (experiencePoints != other.experiencePoints)
			return false;
		if (playerID != other.playerID)
			return false;
		return true;
	}

}
