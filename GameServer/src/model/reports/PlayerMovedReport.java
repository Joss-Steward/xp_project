package model.reports;

import data.Position;
import model.QualifiedObservableReport;

/**
 * Reports movement of any player playing on this server
 * @author Merlin
 *
 */
public final class PlayerMovedReport implements QualifiedObservableReport
{

	private final Position newPosition;
	private final int playerID;
	/**
	 * @param playerID the ID of the player that moved
	 * @param position the position he moved to
	 */
	public PlayerMovedReport(int playerID, Position position)
	{
		newPosition = position;
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
		if (newPosition == null)
		{
			if (other.newPosition != null)
				return false;
		} else if (!newPosition.equals(other.newPosition))
			return false;
		return true;
	}
	/**
	 * @return the newPosition
	 */
	public Position getNewPosition()
	{
		return newPosition;
	}
	/**
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}
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

}
