package model.reports;

import data.Position;
import datasource.DatabaseException;
import model.Player;
import model.QualifiedObservableReport;

/**
 * This report is sent when a player successfully connects to this area server
 * 
 * @author Merlin
 * 
 */
public final class AddExistingPlayerReport implements QualifiedObservableReport
{

	private final int playerID;
	private final String playerName;
	private final String appearanceType;
	private final Position position;
	private int recipientPlayerID;

	/**
	 * @param p
	 *            the player who connected to this server
	 * @throws DatabaseException
	 *             only of something is badly broken at the database - the
	 *             player should be there, so we should be able to retrieve this
	 */
	public AddExistingPlayerReport(int recipientID, int playerID, String playerName, String appearanceType, Position position) 
	{
		this.recipientPlayerID = recipientID;
		this.playerID = playerID;
		this.playerName = playerName;
		this.appearanceType = appearanceType;
		this.position = position;
	}

	

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appearanceType == null) ? 0 : appearanceType.hashCode());
		result = prime * result + playerID;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + recipientPlayerID;
		return result;
	}



	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddExistingPlayerReport other = (AddExistingPlayerReport) obj;
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
		if (recipientPlayerID != other.recipientPlayerID)
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
}

