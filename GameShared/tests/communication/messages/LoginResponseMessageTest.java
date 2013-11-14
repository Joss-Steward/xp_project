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
		LoginResponseMessage msg = new LoginResponseMessage(42,"localhost", 1872, 12345);
		assertEquals(42, msg.getUserID());
		assertEquals("localhost", msg.getHostName());
		assertEquals(1872, msg.getPortNumber());
		assertEquals(12345, msg.getPin(),0.0001);
		assertEquals("Successful login of user " + msg.getUserID(), msg.toString());
	}

}
