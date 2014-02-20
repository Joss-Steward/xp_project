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
	private final String playerName;
	private final Position position;
	
	/**
	 * 
	 * @param playerName The player who moved
	 * @param p Where the player moved to
	 */
	public MovementMessage(String playerName, Position p)
	{
		this.playerName = playerName;
		this.position = p;
	}

	/**
	 * @return the player who moved
	 */
	public String getPlayerName()
	{
		return playerName;
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
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
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
		if (!(obj instanceof MovementMessage))
			return false;
		MovementMessage other = (MovementMessage) obj;
		if (playerName == null)
		{
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
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
		return "Movement Message: playerID = " + playerName  + ", position = " + position;
	}

}
