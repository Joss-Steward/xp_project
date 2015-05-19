package model;

/**
 * packs a player's name with their id
 * @author Merlin
 *
 */
public final class PlayerID implements Comparable<PlayerID>
{

	private final int playerID;
	private final String playerName;

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + playerID;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
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
		PlayerID other = (PlayerID) obj;
		if (playerID != other.playerID)
			return false;
		if (playerName == null)
		{
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		return true;
	}

	/**
	 * @param playerID the id
	 * @param playerName the name
	 */
	public PlayerID(int playerID, String playerName)
	{
		this.playerID = playerID;
		this.playerName = playerName;
	}

	/**
	 * @return the player id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the names
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return playerName;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PlayerID o)
	{
		return playerName.compareTo(o.playerName);
				
	}
}
