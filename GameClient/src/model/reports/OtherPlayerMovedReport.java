package model.reports;

import data.Position;
import model.QualifiedObservableReport;

/**
 * Reports movement of the player playing on this client
 * @author Matt Kujawski
 *
 */
public final class OtherPlayerMovedReport implements QualifiedObservableReport
{

	private final Position thePosition;
	/**
	 * @param position the position he moved to
	 */
	public OtherPlayerMovedReport(Position position)
	{
		thePosition = position;
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
		OtherPlayerMovedReport other = (OtherPlayerMovedReport) obj;
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

}
