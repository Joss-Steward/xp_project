package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import data.Position;

/**
 * Tests a login message
 * @author merlin
 *
 */
public class MovementMessageTest
{
	/**
	 * Make sure its toString is correct
	 */
	@Test
	public void testToString()
	{
		Position position = new Position(42,13);
		MovementMessage msg = new MovementMessage(176, position);
		assertEquals("Movement Message: playerID = 176, position = " + position.toString(), msg.toString());
	}

}
