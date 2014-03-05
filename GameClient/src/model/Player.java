package model;

import model.reports.OtherPlayerMovedReport;
import data.Position;

/**
 * Holds the information about one player in the system
 * @author merlin
 *
 */
 public class Player extends QualifiedObservable
{

	protected final int id;
	protected String name;
	protected Position position;
	protected String appearanceType;

	/**
	 * Create a player 
	 * @param playerID the unique ID of this player
	 */
	public Player(int playerID)
	{
		this.id = playerID;
		this.position = new Position(0, 0);
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
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}


	/**
	 * Get the unique ID name for this player
	 * @return the player ID
	 */
	public int getID()
	{
		return id;
	}


	/**
	 * get this player's unique name
	 * @return the name
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * @return this player's appearance type
	 */
	public String getAppearanceType()
	{
		return this.appearanceType;
	}


	/**
	 * set this player's name
	 * @param playerName the new name
	 */
	public void setName(String playerName)
	{
		this.name = playerName;
	}
	
	/**
	 * Set this player's appearance
	 * @param appearanceType the new appearance type
	 */
	public void setAppearanceType(String appearanceType)
	{
		this.appearanceType = appearanceType;
	}
	
	/**
	 * Move the player's position
	 * @param playerPosition
	 * 			The new location the player is
	 * Assuming a valid position.  Error checking else where
	 */
	public void move(Position playerPosition)
	{
		this.position = playerPosition;
		this.notifyObservers(new OtherPlayerMovedReport(playerPosition));
	}
	
	/**
	 * Get the player's position
	 * @return playerPosition
	 * 			Returns the player position. If a position is not set should return null.
	 */
	public Position getPosition()
	{
		return this.position;
	}

}
