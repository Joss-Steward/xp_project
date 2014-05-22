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
	private int playerID;
	private double pin;

	/**
	 * @param playerID
	 *            the player ID we should use to connect
	 * @param pin
	 *            the pin we were given to validate this connection request
	 */
	public ConnectMessage(int playerID, double pin)
	{
		this.playerID = playerID;
		this.pin = pin;
	}

	/**
	 * @return the playerID
	 */
	public int getPlayerID()
	{
		return playerID;
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
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Connect Message: playerID = " + playerID + " and pin = " + pin;
	}
}
