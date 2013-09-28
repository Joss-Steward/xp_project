package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests a login message
 * @author merlin
 *
 */
public class PlayerJoinedMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToString()
	{
		PlayerJoinedMessage msg = new PlayerJoinedMessage("fred");
		assertEquals("PlayerJoined Message: userName = fred", msg.toString());
		assertEquals("fred", msg.getUserName());
	}
	
}
