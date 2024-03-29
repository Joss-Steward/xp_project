package communication.messages;

import java.io.Serializable;

/**
 * @author Merlin
 * 
 */
public class ConnectMessage implements Message, Serializable
{

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(pin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + playerID;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectMessage other = (ConnectMessage) obj;
		if (playerID != other.playerID)
			return false;
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int playerID;
	private double pin;

	/**
	 * @param playerID the player ID we should use to connect
	 * @param pin the pin we were given to validate this connection request
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
