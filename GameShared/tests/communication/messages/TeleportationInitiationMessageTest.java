package communication.messages;

import static org.junit.Assert.assertEquals;
import model.ServersInDB;

import org.junit.Test;

import data.Position;

/**
 * Tests a login message
 * 
 * @author merlin
 * 
 */
public class TeleportationInitiationMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testInitialization()
	{
		TeleportationInitiationMessage msg = new TeleportationInitiationMessage(1, ServersInDB.FIRST_SERVER.getMapName(), new Position(4,3));
		assertEquals("TeleportationInitiationMessage: playerID = 1 mapName = " + ServersInDB.FIRST_SERVER.getMapName() + " position = " + msg.getPosition(), msg.toString());
		assertEquals(ServersInDB.FIRST_SERVER.getMapName(), msg.getMapName());
		assertEquals(1, msg.getPlayerId());
		assertEquals(new Position(4,3), msg.getPosition());
	}

	
}
