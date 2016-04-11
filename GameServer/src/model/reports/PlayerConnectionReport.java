package model.reports;

import model.QualifiedObservableReport;
import data.Crew;
import data.Major;
import data.Position;

/**
 * This report is sent when a player successfully connects to this area server
 * 
 * @author Merlin
 * 
 */
public final class PlayerConnectionReport implements QualifiedObservableReport
{

	private final int playerID;
	private final String playerName;
	private final String appearanceType;
	private final Position position;
	private Crew crew;
	private Major major;

	/**
	 * Information about a player who has just joined this server
	 * @param playerID the player's ID
	 * @param playerName the player's name
	 * @param appearanceType the player's appearance type
	 * @param position where the player is standing
	 * @param crew the crew to which the player belongs
	 * @param major the major of this player
	 */
	public PlayerConnectionReport(int playerID, String playerName, String appearanceType, Position position, Crew crew, Major major)
	{
		this.playerID = playerID;
		this.playerName = playerName;
		this.appearanceType = appearanceType;
		this.position = position;
		this.crew = crew;
		this.major = major;
	}

	/**
	 * @return the appearance type for this player
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @return the crew to which this player belongs
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * @return the player's unique ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the player's name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @return the player's major
	 */
	public Major getMajor()
	{
		return major;
	}
	
	/**
	 * Get this player's position on this area's map
	 * 
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appearanceType == null) ? 0 : appearanceType.hashCode());
		result = prime * result + ((crew == null) ? 0 : crew.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + playerID;
		result = prime * result
				+ ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerConnectionReport other = (PlayerConnectionReport) obj;
		if (appearanceType == null) {
			if (other.appearanceType != null)
				return false;
		} else if (!appearanceType.equals(other.appearanceType))
			return false;
		if (crew != other.crew)
			return false;
		if (major != other.major)
			return false;
		if (playerID != other.playerID)
			return false;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
}
