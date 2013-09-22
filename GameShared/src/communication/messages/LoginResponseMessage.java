package communication.messages;

import java.io.Serializable;

/**
 * The message that goes from the main server to the client on a successful
 * login. It tells the client what the user's ID number is and where to connect
 * to begin playing.
 * 
 * @author Merlin
 * 
 */
public class LoginResponseMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userID;
	private String hostName;
	private int portNumber;
	private int pin;

	/**
	 * 
	 * @param userID
	 *            the ID of the user that logged in
	 * @param hostName
	 *            the host name of the first area server the client should
	 *            connect to
	 * @param portNumber
	 *            the port number of the first area server the client should
	 *            connect to
	 * @param pin
	 *            the magic number required to connect to area servers
	 */
	public LoginResponseMessage(int userID, String hostName, int portNumber, int pin)
	{
		this.userID = userID;
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.pin = pin;
	}

	/**
	 * @return the pin
	 */
	public int getPin()
	{
		return pin;
	}

	/**
	 * 
	 * @return the userID of the user that just logged in
	 */
	public int getUserID()
	{
		return userID;
	}

	/**
	 * 
	 * @return the host name of the area server where this player should start
	 *         playing
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * 
	 * @return the port number of the area server where this player should start
	 *         playing
	 */
	public int getPortNumber()
	{
		return portNumber;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Successful login of user " + userID;
	}

}
