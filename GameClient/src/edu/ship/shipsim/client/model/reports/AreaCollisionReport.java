package edu.ship.shipsim.client.model.reports;

import model.QualifiedObservableReport;
import communication.messages.AreaCollisionMessage;

/**
 * 
 * @author nhydock
 *
 */
public class AreaCollisionReport implements QualifiedObservableReport {
	private final int playerID;
	private final String areaName;
	
	/**
	 * 
	 * @param playerID
	 *            The player who moved
	 * @param areaName
	 *            The name of the area the player has stepped into
	 */
	public AreaCollisionReport(int playerID, String areaName)
	{
		this.playerID = playerID;
		this.areaName = areaName;
	}

	/**
	 * @return the player who moved
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + playerID;
		result = prime * result + ((areaName == null) ? 0 : areaName.hashCode());
		return result;
	}

	/**
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AreaCollisionMessage))
			return false;
		
		AreaCollisionReport other = (AreaCollisionReport) obj;
		
		boolean equals = true;
		
		if(this.getAreaName() == null) {
			equals = equals && other.getAreaName() == null;
		} else {
			equals = equals && this.getAreaName().equals(other.getAreaName());
		}
		equals = equals && playerID == other.playerID;
		
		return equals;
	}

	/**
	 * @return the position to which the player moved
	 */
	public String getAreaName()
	{
		return areaName;
	}
}
