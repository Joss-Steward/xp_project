package model.reports;

import data.AdventureStateEnum;
import model.QualifiedObservableReport;

/**
 * @author sl6469, cody
 *
 */
public final class AdventureStateChangeReport implements QualifiedObservableReport
{

	private final int adventureID;
	private String adventureDescription;
	private AdventureStateEnum newState;
	
	/**
	 * @param adventureID unique adventure ID
	 * @param adventureDescription description of adventure
	 * @param newState state the adventure has moved to for this client player
	 */
	public AdventureStateChangeReport(int adventureID, String adventureDescription, AdventureStateEnum newState)
	{
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.newState = newState;
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
		AdventureStateChangeReport other = (AdventureStateChangeReport) obj;
		if (adventureID != other.adventureID)
			return false;
		return true;
	}
	
	/**
	 * @return the state the adventure has moved to
	 */
	public AdventureStateEnum getNewState()
	{
		return newState;
	}

	/**
	 * @return the description of the adventure whose state has changed
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return the adventureID that needs the report
	 */
	public int getadventureID()
	{
		return adventureID;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + adventureID;
		return result;
	}

}
