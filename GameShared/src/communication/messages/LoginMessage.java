package communication.messages;

import java.io.Serializable;

/**
 * Used to log a player into a server
 * 
 * @author merlin
 * 
 */
public class LoginMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String playerName;
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
	 * @return the player name that is being logged on
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * 
	 * @param playerName
	 *            the player's name
	 * @param password
	 *            the player's password
	 */
	public LoginMessage(String playerName, String password)
	{
		this.playerName = playerName;
		this.password = password;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Login Message: playerName = " + playerName + " and password = "
				+ password;
	}

}
