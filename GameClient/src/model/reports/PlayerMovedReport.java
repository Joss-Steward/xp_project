package model.reports;

import data.Position;
import model.QualifiedObservableReport;

/**
 * Reports movement of the player playing on this client
 * @author Matt Kujawski
 *
 */
public final class PlayerMovedReport implements QualifiedObservableReport
{
	private final int playerID;
	private final Position thePosition;
	/**
	 * @param playerID the unique identifier of the player moving
	 * @param position the position he moved to
	 */
	public PlayerMovedReport(int playerID, Position position)
	{
		thePosition = position;
		this.playerID = playerID;
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
		PlayerMovedReport other = (PlayerMovedReport) obj;
		if (thePosition == null)
		{
			if (other.thePosition != null)
				return false;
		} else if (!thePosition.equals(other.thePosition))
			return false;
		return true;
	}
	/**
	 * @return the thePosition
	 */
	public Position getNewPosition()
	{
		return thePosition;
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((thePosition == null) ? 0 : thePosition.hashCode());
		return result;
	}
	
	/**
	 * @return the id of the player that is moving
	 */
	public int getID()
	{
		return playerID;
	}

}
