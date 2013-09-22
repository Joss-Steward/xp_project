package communication.messages;

import java.io.Serializable;

/**
 * @author Merlin
 *
 */
public class ConnectMessage implements Message, Serializable
{

	private int userID;
	private int pin;

	/**
	 * @param userID
	 * @param pin
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
