package communication.messages;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests a file message which is designed to carry a tmx file
 * 
 * @author merlin
 * 
 */
public class MapFileMessageTest
{
	/**
	 * Make sure its toString is correct
	 * 
	 * @throws Exception shouldn't
	 */
	@Test
	public void testToString() throws Exception
	{
		MapFileMessage msg = new MapFileMessage("current.tmx");
		assertEquals("current.tmx", msg.toString());
		assertEquals("current.tmx", msg.getMapFileName());
	}

}
