package communication.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import datasource.ServersInDB;

/**
 * Tests a login message
 * 
 * @author merlin
 * 
 */
public class TeleportationContinuationMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToStringAndGetters()
	{
		TeleportationContinuationMessage msg = new TeleportationContinuationMessage(
				ServersInDB.FIRST_SERVER.getMapName(),
				ServersInDB.FIRST_SERVER.getHostName(),
				ServersInDB.FIRST_SERVER.getPortNumber(), 2, 4);
		assertTrue(msg.toString().startsWith(
				"TeleportationContinuationMessage: mapName = "
						+ ServersInDB.FIRST_SERVER.getMapName() + " and hostName = "
						+ ServersInDB.FIRST_SERVER.getHostName() + " and portNumber = "
						+ ServersInDB.FIRST_SERVER.getPortNumber()));
		assertEquals(ServersInDB.FIRST_SERVER.getMapName(), msg.getMapName());
		assertEquals(ServersInDB.FIRST_SERVER.getHostName(), msg.getHostName());
		assertEquals(ServersInDB.FIRST_SERVER.getPortNumber(), msg.getPortNumber());
		assertEquals(2, msg.getPlayerID());
		assertEquals(4, msg.getPin());
	}

}
