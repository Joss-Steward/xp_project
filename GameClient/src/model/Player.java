package model;

/**
 * Holds the information about one player in the system
 * @author merlin
 *
 */
 public class Player
{

	private final int playerID;
	private String playerName;

	/**
	 * Create a player 
	 * @param playerID the unique ID of this player
	 */
	public Player(int playerID)
	{
		this.playerID = playerID;
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
		if (!(obj instanceof Player))
			return false;
		Player other = (Player) obj;
		if (playerID != other.playerID)
			return false;
		return true;
	}


	/**
	 * Get the unique ID name for this player
	 * @return the player ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}


	/**
	 * get this player's unique name
	 * @return the name
	 */
	public String getPlayerName()
	{
		return this.playerName;
	}


	/**
	 * set this player's name
	 * @param playerName the new name
	 */
	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

}
