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
	private final String userName;

	/**
	 * @param userName the name of the new player
	 */
	public PlayerJoinedMessage(String userName)
	{
		this.userName = userName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}

}
