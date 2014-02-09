package model;

/**
 * Holds the information about one player in the system
 * @author merlin
 *
 */
 class Player
{

	private final String playerName;

	/**
	 * Create a player 
	 * @param playerName the unique name of this player
	 */
	public Player(String playerName)
	{
		this.playerName = playerName;
	}

	@Override
	public final int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;
		if (playerName == null)
		{
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		return true;
	}

	/**
	 * Get the unique user name for this player
	 * @return the user name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

}
