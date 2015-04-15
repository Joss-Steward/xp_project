package communication.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import communication.messages.ExperienceChangedMessage;
import datasource.PlayersForTest;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * @author Ryan
 *
 */
public class ExperienceChangedMessageHandlerTest {

	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void reset()
	{
		ModelFacade.resetSingleton();
	}
	
	/**
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		ExperienceChangedMessageHandler h = new ExperienceChangedMessageHandler();
		assertEquals(ExperienceChangedMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * Testing to see if a command is queued after receiving a message
	 */
	@Test
	public void handleExperienceChangedMessage()
	{
		reset();
		
		ExperienceChangedMessage msg = new ExperienceChangedMessage(PlayersForTest.JOHN.getExperiencePoints(), PlayersForTest.JOHN.getPlayerID());
		ExperienceChangedMessageHandler h = new ExperienceChangedMessageHandler();
		h.process(msg);

		assertEquals(1,ModelFacade.getSingleton().queueSize());
		
		reset();
	}
}
