package edu.ship.shipsim.areaserver.model.reports;

import data.Position;
import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.model.Player;
import model.QualifiedObservableReport;

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

	/**
	 * @param p
	 *            the player who connected to this server
	 * @throws DatabaseException
	 *             only of something is badly broken at the database - the
	 *             player should be there, so we should be able to retrieve this
	 */
	public PlayerConnectionReport(Player p) throws DatabaseException
	{
		this.playerID = p.getPlayerID();
		this.playerName = p.getPlayerName();
		this.appearanceType = p.getAppearanceType();
		this.position = p.getPlayerPosition();
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
		if (!(obj instanceof PlayerConnectionReport))
			return false;
		PlayerConnectionReport other = (PlayerConnectionReport) obj;
		if (appearanceType == null)
		{
			if (other.appearanceType != null)
				return false;
		} else if (!appearanceType.equals(other.appearanceType))
			return false;
		if (playerID != other.playerID)
			return false;
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
	 * @return the appearance type for this player
	 */
	public String getAppearanceType()
	{
		return appearanceType;
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
	 * Get this player's position on this area's map
	 * 
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
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
		result = prime * result
				+ ((appearanceType == null) ? 0 : appearanceType.hashCode());
		result = prime * result + playerID;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

}
