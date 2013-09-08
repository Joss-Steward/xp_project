package communication.messages;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Sent from the server to the client upon successful login to the system
 * @author Merlin
 *
 */
public class LoginResponseMessageTest
{

	/**
	 * Just make sure it holds the right stuff
	 */
	@Test
	public void basic()
	{
		LoginResponseMessage msg = new LoginResponseMessage(42,"hostname", 1871);
		assertEquals(42, msg.getUserID());
		assertEquals("hostname", msg.getHostName());
		assertEquals(1871, msg.getPortNumber());
		assertEquals("Successful login of user " + msg.getUserID(), msg.toString());
	}

}
