package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests a login message
 * @author merlin
 *
 */
public class LoginMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToString()
	{
		LoginMessage msg = new LoginMessage("fred");
		assertEquals("Login Message: userName = fred", msg.toString());
		assertEquals("fred", msg.getUserName());
	}

}
