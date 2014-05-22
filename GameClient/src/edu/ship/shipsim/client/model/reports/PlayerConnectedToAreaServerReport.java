package edu.ship.shipsim.client.model.reports;

import data.Position;
import model.QualifiedObservableReport;

/**
 * Is sent when the player on this client has completed the connection to an
 * area server (must be sent AFTER the map report has been sent)
 * 
 * @author Merlin
 * 
 */
public class PlayerConnectedToAreaServerReport implements QualifiedObservableReport
{
	private int playerID;
	private String playerName;
	private String appearanceType;
	private Position position;
	private boolean isThisClientsPlayer;

	/**
	 * 
	 * @param playerID
	 *            the id of the player
	 * @param playerName
	 *            the name of the player
	 * @param appearanceType
	 *            the type of the player
	 * @param position
	 *            the position of the player
	 * @param isThisClientsPlayer
	 *            statement saying if the player connected was the one
	 *            controlled by the client
	 * @see view.PlayerType
	 */
	public PlayerConnectedToAreaServerReport(int playerID, String playerName,
			String appearanceType, Position position, boolean isThisClientsPlayer)
	{
		this.playerID = playerID;
		this.playerName = playerName;
		this.appearanceType = appearanceType;
		this.position = position;
		this.isThisClientsPlayer = isThisClientsPlayer;
	}

	/**
	 * Report the name this player used to login to the system
	 * 
	 * @return the name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * Report the player type which reflects how this player should be
	 * displayed. This must be a string that matches the name of one of the
	 * members of the PlayerType enum.
	 * 
	 * @return the player appearance type
	 */
	public String getPlayerAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @return the player id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the player's position
	 */
	public Position getPlayerPosition()
	{
		return position;
	}

	/**
	 * @return if the player connected was the client's player
	 */
	public boolean isThisClientsPlayer()
	{
		return isThisClientsPlayer;
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
		PlayerConnectedToAreaServerReport other = (PlayerConnectedToAreaServerReport) obj;
		boolean result = true;
		result = result && this.getPlayerID() == other.getPlayerID();
		result = result
				&& this.getPlayerAppearanceType().equals(other.getPlayerAppearanceType());
		result = result && this.getPlayerName().equals(other.getPlayerName());
		result = result && this.getPlayerPosition().equals(other.getPlayerPosition());
		result = result && this.isThisClientsPlayer() == other.isThisClientsPlayer();
		return result;
	}
}
