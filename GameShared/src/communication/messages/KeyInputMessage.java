package communication.messages;

import java.io.Serializable;

/**
 * A message for user key input.
 * 
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputMessage implements Message, Serializable
{

	private static final long serialVersionUID = 1L;
	private String input;

	/**
	 * @param input user key input
	 */
	public KeyInputMessage(String input)
	{
		this.input = input;
	}

	/**
	 * @return user key input
	 */
	public String getInput()
	{
		return input;
	}

}
