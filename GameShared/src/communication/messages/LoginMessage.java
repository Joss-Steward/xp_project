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
	private String password;

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

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
	 * @param password the player's password
	 */
	public LoginMessage(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Login Message: userName = " + userName + " and password = " + password;
	}
	
	
}
