package model.reports;

import data.Position;
import model.QualifiedObservableReport;

/**
 * Reports movement of the player playing on this client
 * @author Merlin
 *
 */
public class ThisPlayerMovedReport implements QualifiedObservableReport
{

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newPosition == null) ? 0 : newPosition.hashCode());
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
		ThisPlayerMovedReport other = (ThisPlayerMovedReport) obj;
		if (newPosition == null)
		{
			if (other.newPosition != null)
				return false;
		} else if (!newPosition.equals(other.newPosition))
			return false;
		return true;
	}
	private Position newPosition;
	/**
	 * @param position the position he moved to
	 */
	public ThisPlayerMovedReport(Position position)
	{
		newPosition = position;
	}
	/**
	 * @return the newPosition
	 */
	public Position getNewPosition()
	{
		return newPosition;
	}

}
