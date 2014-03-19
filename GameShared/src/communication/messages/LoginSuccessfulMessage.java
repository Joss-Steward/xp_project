package communication.messages;

import java.io.Serializable;

/**
 * The message that goes from the main server to the client on a successful
 * login. It tells the client what the player's ID number is and where to
 * connect to begin playing.
 * 
 * @author Merlin
 * 
 */
public class LoginSuccessfulMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int playerID;
	private String hostName;
	private int portNumber;
	private double pin;

	/**
	 * 
	 * @param playerID
	 *            the ID of the player that logged in
	 * @param hostName
	 *            the host name of the first area server the client should
	 *            connect to
	 * @param portNumber
	 *            the port number of the first area server the client should
	 *            connect to
	 * @param pin
	 *            the magic number required to connect to area servers
	 */
	public LoginSuccessfulMessage(int playerID, String hostName, int portNumber,
			double pin)
	{
		this.playerID = playerID;
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.pin = pin;
	}

	/**
	 * @return the pin
	 */
	public double getPin()
	{
		return pin;
	}

	/**
	 * 
	 * @return the player ID of the player that just logged in
	 */
	public int getPlayerID()
	{
		return playerID;
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
		return "Successful login of player " + playerID;
	}

}
