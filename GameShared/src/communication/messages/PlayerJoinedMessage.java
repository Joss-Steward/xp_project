package communication.messages;

import java.io.Serializable;

/**
 * @author Merlin
 *
 */
public class PlayerJoinedMessage implements Message, Serializable
{

	private final String userName;

	/**
	 * @param userName
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
