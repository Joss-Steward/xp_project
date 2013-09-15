package communication.messages;

import static org.junit.Assert.assertEquals;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.Position;

/**
 * Tests a login message
 * 
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
		Position position = new Position(42, 13);
		MovementMessage msg = new MovementMessage(176, position);
		assertEquals(176, msg.getPlayerID());
		assertEquals(position, msg.getPosition());
		assertEquals("Movement Message: playerID = 176, position = " + position.toString(),
				msg.toString());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(MovementMessage.class).verify();
	}

}
