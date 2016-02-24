package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import testData.PlayersForTest;

/**
 * Tests a login message
 * 
 * @author merlin
 * 
 */
public class PlayerJoinedMessageTest
{
	/**
	 * Make sure the object is built and its toString is correct
	 */
	@Test
	public void testToStringAndConstructor()
	{
		PlayerJoinedMessage msg = new PlayerJoinedMessage(2,
				PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getAppearanceType(), PlayersForTest.MERLIN.getPosition(), PlayersForTest.MERLIN.getCrew());
		assertEquals("PlayerJoined Message: playerName = Merlin", msg.toString());
		assertEquals(2, msg.getPlayerID());
		assertEquals("Merlin", msg.getPlayerName());
		assertEquals(PlayersForTest.MERLIN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(PlayersForTest.MERLIN.getPosition(), msg.getPosition());
		assertEquals(PlayersForTest.MERLIN.getCrew(), msg.getCrew());
	}

}
