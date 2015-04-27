package communication.handlers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import communication.messages.AdventureStateChangeMessage;
import datasource.AdventureStateEnum;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * Tests functionality of the AdventureStateChangeMessageHandler
 * @author Ryan
 *
 */
public class AdventureStateChangeMessageHandlerTest 
{

	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void setUp()
	{
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
	}
	
	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void testTypeWeHandle()
	{
		AdventureStateChangeMessageHandler h = new AdventureStateChangeMessageHandler();
		assertEquals(AdventureStateChangeMessage.class, h.getMessageTypeWeHandle());
	}
	

	/**
	 * Test that the handler messages handles the messages and creates
	 * a command
	 */
	@Test
	public void testMessageHandling()
	{
		AdventureStateChangeMessageHandler h = new AdventureStateChangeMessageHandler();
		AdventureStateChangeMessage msg = new AdventureStateChangeMessage(1, 2, 3, "Big Adventure", AdventureStateEnum.PENDING);
		
		h.process(msg);
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
	}

}
