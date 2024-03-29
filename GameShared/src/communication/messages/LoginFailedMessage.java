package communication.messages;

import java.io.Serializable;

/**
 * The message that goes from the main server to the client when the user
 * enterse invalid login credentials.
 * 
 * @author Merlin
 * 
 */
public class LoginFailedMessage implements Message, Serializable
{

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result;
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
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 */
	public LoginFailedMessage()
	{

	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Failed login";
	}

}
