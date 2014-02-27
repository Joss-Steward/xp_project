package communication.messages;

import static org.junit.Assert.assertEquals;
import model.PlayersInDB;

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
		PlayerJoinedMessage msg = new PlayerJoinedMessage(2, PlayersInDB.MERLIN.getPlayerName(),
				PlayersInDB.MERLIN.getAppearanceType(), PlayersInDB.MERLIN.getPosition());
		assertEquals("PlayerJoined Message: playerName = Merlin", msg.toString());
		assertEquals(2, msg.getPlayerID());
		assertEquals("Merlin", msg.getPlayerName());
		assertEquals(PlayersInDB.MERLIN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(PlayersInDB.MERLIN.getPosition(), msg.getPosition());
	}
	
}
