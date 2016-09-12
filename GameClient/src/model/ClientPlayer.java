package model;

import java.util.Observable;

import model.QualifiedObservableConnector;
import model.reports.ChangeMapReport;
import model.reports.ThisClientsPlayerMovedReport;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

/**
 * Holds the information about one player in the system
 * 
 * @author merlin
 * 
 */
public class ClientPlayer extends Observable
{

	protected final int id;
	protected String name;
	protected Position position;
	protected String appearanceType;
	private Crew crew;
	private Major major;

	/**
	 * Create a player
	 * 
	 * @param playerID
	 *            the unique ID of this player
	 */
	public ClientPlayer(int playerID)
	{
		this.id = playerID;
		this.position = new Position(0, 0);
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appearanceType == null) ? 0 : appearanceType.hashCode());
		result = prime * result + ((crew == null) ? 0 : crew.hashCode());
		result = prime * result + id;
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ClientPlayer))
			return false;
		ClientPlayer other = (ClientPlayer) obj;
		if (appearanceType == null) {
			if (other.appearanceType != null)
				return false;
		} else if (!appearanceType.equals(other.appearanceType))
			return false;
		if (crew != other.crew)
			return false;
		if (id != other.id)
			return false;
		if (major != other.major)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
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
		QualifiedObservableConnector.getSingleton().sendReport(
				new ThisClientsPlayerMovedReport(this.id, playerPosition));
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
				new ChangeMapReport(id, hotSpot.getTeleportPosition(), hotSpot
						.getMapName()));
	}

	/**
	 * @return the crew this player belongs to
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * 
	 * @param crew
	 *            the crew this player should belong to
	 */
	protected void setCrew(Crew crew)
	{
		this.crew = crew;
	}

	/**
	 * @return the major of the player
	 */
	public Major getMajor()
	{
		return major;
	}

	/**
	 * @param major of the player
	 */
	public void setMajor(Major major) {
		this.major = major;
	}

}
