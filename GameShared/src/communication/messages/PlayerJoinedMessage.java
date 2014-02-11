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

	/**
	 * @param playerName the name of the new player
	 */
	public PlayerJoinedMessage(String playerName)
	{
		this.playerName = playerName;
	}

	/**
	 * @return the userName
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
