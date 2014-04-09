package communication.messages;

import static org.junit.Assert.assertEquals;
import model.ServersInDB;

import org.junit.Test;

/**
 * Tests a login message
 * 
 * @author merlin
 * 
 */
public class GetServerInfoMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToString()
	{
		GetServerInfoMessage msg = new GetServerInfoMessage(ServersInDB.FIRST_SERVER.getMapName());
		assertEquals("GetServerInfoMessage: mapName = " + ServersInDB.FIRST_SERVER.getMapName(), msg.toString());
		assertEquals(ServersInDB.FIRST_SERVER.getMapName(), msg.getMapName());
	}

}
