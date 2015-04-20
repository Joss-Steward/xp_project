package communication.messages;

import java.io.Serializable;

import data.Position;

/**
 * Sent to all clients when a new player connects to an area server
 * 
 * @author Merlin
 * 
 */
public class PlayerJoinedMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String playerName;
	private int playerID;
	private String appearanceType;
	private Position position;

	/**
	 * @param playerID
	 *            the unique ID of the player
	 * @param playerName
	 *            the name of the new player
	 * @param position
	 *            where this player is on the map on this server
	 * @param appearanceType
	 *            the way the player should be drawn on the screen
	 */
	public PlayerJoinedMessage(int playerID, String playerName, String appearanceType,
			Position position)
	{
		this.playerID = playerID;
		this.playerName = playerName;
		this.appearanceType = appearanceType;
		this.position = position;
	}

	/**
	 * get this player's unique ID
	 * 
	 * @return the player's ID
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
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "PlayerJoined Message: playerName = " + playerName;
	}

	/**
	 * Get the appearance type that shows how this player wants to be displayed
	 * 
	 * @return the appearance type
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * Get the position this player is in
	 * 
	 * @return the position
	 */
	public Position getPosition()
	{
		return position;
	}
}
