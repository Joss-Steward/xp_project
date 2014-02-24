package communication.messages;

import java.io.Serializable;

/**
 * Sent to all clients when a new player connects to an area server
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

	/**
	 * @param playerID the unique ID of the player
	 * @param playerName the name of the new player
	 */
	public PlayerJoinedMessage(int playerID, String playerName)
	{
		this.playerID = playerID;
		this.playerName = playerName;
	}

	/**
	 * get this player's unique ID
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
}
