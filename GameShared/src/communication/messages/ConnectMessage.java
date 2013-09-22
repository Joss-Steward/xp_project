package communication.messages;

import java.io.Serializable;

/**
 * @author Merlin
 *
 */
public class ConnectMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userID;
	private int pin;

	/**
	 * @param userID the user ID we should use to connect
	 * @param pin the pin we were given to validate this connection request
	 */
	public ConnectMessage(int userID, int pin)
	{
		this.userID = userID;
		this.pin = pin;
	}

	/**
	 * @return the userID
	 */
	public int getUserID()
	{
		return userID;
	}

	/**
	 * @return the pin
	 */
	public int getPin()
	{
		return pin;
	}

}
