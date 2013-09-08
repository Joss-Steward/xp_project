package communication.messages;

import java.io.Serializable;

import data.Position;



/**
 * Encodes the fact that a player has moved to a given location
 * @author merlin
 *
 */
public class MovementMessage implements Message, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int playerID;
	private Position position;
	
	/**
	 * 
	 * @param playerID The player who moved
	 * @param p Where the player moved to
	 */
	public MovementMessage(int playerID, Position p)
	{
		this.playerID = playerID;
		this.position = p;
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
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + playerID;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	/**
	 * 
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
		MovementMessage other = (MovementMessage) obj;
		if (playerID != other.playerID)
			return false;
		if (position == null)
		{
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	/**
	 * @return the position to which the player moved
	 */
	public Position getPosition()
	{
		return position;
	}
	
	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Movement Message: playerID = " + playerID  + ", position = " + position;
	}

}
