package edu.ship.shipsim.client.model;

import java.util.Observable;

import model.QualifiedObservableConnector;
import data.Position;
import edu.ship.shipsim.client.model.reports.ChangeMapReport;
import edu.ship.shipsim.client.model.reports.PlayerMovedReport;

/**
 * Holds the information about one player in the system
 * 
 * @author merlin
 * 
 */
public class Player extends Observable
{

	protected final int id;
	protected String name;
	protected Position position;
	protected String appearanceType;

	/**
	 * Create a player
	 * 
	 * @param playerID
	 *            the unique ID of this player
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
	 * 
	 * @return the player ID
	 */
	public int getID()
	{
		return id;
	}

	/**
	 * get this player's unique name
	 * 
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
	 * 
	 * @param playerName
	 *            the new name
	 */
	public void setName(String playerName)
	{
		this.name = playerName;
	}

	/**
	 * Set this player's appearance
	 * 
	 * @param appearanceType
	 *            the new appearance type
	 */
	public void setAppearanceType(String appearanceType)
	{
		this.appearanceType = appearanceType;
	}

	/**
	 * Move the player's position
	 * 
	 * @param playerPosition
	 *            The new location the player is Assuming a valid position.
	 *            Error checking else where
	 */
	public void move(Position playerPosition)
	{
		this.position = playerPosition;
		QualifiedObservableConnector.getSingleton().sendReport(this,
				new PlayerMovedReport(this.id, playerPosition));
	}

	/**
	 * Forcibly sets the player's position without notifying observers. </p>
	 * Should only be called when the player is initialized.
	 * 
	 * @param playerPosition
	 *            The new location of the player
	 */
	public void setPosition(Position playerPosition)
	{
		this.position = playerPosition;
	}

	/**
	 * Get the player's position
	 * 
	 * @return playerPosition Returns the player position. If a position is not
	 *         set should return null.
	 */
	public Position getPosition()
	{
		return this.position;
	}

	/**
	 * @param thePosition
	 *            is the position of the hotspot Creates a ChangeMapReport with
	 *            the Map and location the player will teleport to.
	 */
	public void teleport(Position thePosition)
	{
		TeleportHotSpot hotSpot = MapManager.getSingleton().getTeleportHotSpot(
				thePosition);
		QualifiedObservableConnector.getSingleton().sendReport(
				this,
				new ChangeMapReport(id, hotSpot.getTeleportPosition(), hotSpot
						.getMapName()));
	}

}
