package communication.messages;

import java.io.Serializable;

/**
 * Used to log a player into a server
 * @author merlin
 *
 */
public class LoginMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;

	/**
	 * 
	 * @return the user name that is being logged on
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * 
	 * @param userName the player's user name
	 */
	public LoginMessage(String userName)
	{
		this.userName = userName;
	}
	
	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Login Message: userName = " + userName;
	}
	
	
}
