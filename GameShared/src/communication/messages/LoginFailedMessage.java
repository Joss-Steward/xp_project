package communication.messages;

import java.io.Serializable;

/**
 * The message that goes from the main server to the client when the user enterse invalid 
 * login credentials.
 * 
 * @author Merlin
 * 
 */
public class LoginFailedMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 */
	public LoginFailedMessage()
	{

	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Failed login";
	}

}
