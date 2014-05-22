package communication.handlers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import communication.messages.MovementMessage;
import data.Position;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * @author Andrew
 * 
 *         Test to see if MovementMessagHandler properly works.
 */
public class MovementMessageHandlerTest
{
	/**
	 * reset the singletons and tell the model we are running headless
	 */
	@Before
	public void setUp()
	{
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, true);
	}

	/**
	 * Tests to see if the command is built correctly, and added to the Facade.
	 * 
	 * @throws InterruptedException
	 *             shouldn't
	 */
	@Test
	public void engineNotified() throws InterruptedException
	{
		Position p = new Position(1, 1);
		MovementMessage msg = new MovementMessage(12, p);
		MovementMessageHandler handler = new MovementMessageHandler();
		handler.process(msg);

		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
	}

}
