package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datasource.PlayersForTest;

/**
 * Tests a login message
 * 
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
		PlayerJoinedMessage msg = new PlayerJoinedMessage(2,
				PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getAppearanceType(), PlayersForTest.MERLIN.getPosition());
		assertEquals("PlayerJoined Message: playerName = Merlin", msg.toString());
		assertEquals(2, msg.getPlayerID());
		assertEquals("Merlin", msg.getPlayerName());
		assertEquals(PlayersForTest.MERLIN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(PlayersForTest.MERLIN.getPosition(), msg.getPosition());
	}

}
