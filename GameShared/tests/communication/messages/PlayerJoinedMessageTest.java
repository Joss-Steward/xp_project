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
		PlayerJoinedMessage msg = new PlayerJoinedMessage(3, "fred");
		assertEquals("PlayerJoined Message: playerName = fred", msg.toString());
		assertEquals(3, msg.getPlayerID());
		assertEquals("fred", msg.getPlayerName());
	}
	
}
