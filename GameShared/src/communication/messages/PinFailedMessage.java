package communication.messages;

import java.io.Serializable;

/**
 * The message that goes from the main server to the client when the user has a pin error
 * 
 * Matt and Andy
 */
public class PinFailedMessage implements Message, Serializable
{
	private final String err;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg the message
	 */
	public PinFailedMessage(String msg)
	{
		err = msg;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return err;
	}

}
