package model;

import model.reports.OtherPlayerMovedReport;
import data.Position;

/**
 * Holds the information about one player in the system
 * @author merlin
 *
 */
 class Player extends QualifiedObservable
{

	private final String playerName;
	private Position playerPosition;

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
	 * Get the unique player name for this player
	 * @return the player name
	 */
	public String getPlayerName()
	{
		return playerName;
	}
	
	/**
	 * Move the player's position
	 * @param playerPosition
	 * 			The new location the player is
	 * Assuming a valid position.  Error checking else where
	 */
	public void move(Position playerPosition)
	{
		this.playerPosition = playerPosition;
		this.notifyObservers(new OtherPlayerMovedReport(playerPosition));
	}
	
	/**
	 * Get the player's position
	 * @return playerPosition
	 * 			Returns the player position. If a position is not set should return null.
	 */
	public Position getPlayerPosition()
	{
		return this.playerPosition;
	}

}
