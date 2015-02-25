package communication.handlers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import communication.messages.AreaCollisionMessage;
import edu.ship.shipsim.areaserver.model.ModelFacade;

/**
 * 
 * @author Ethan
 *
 */
public class AreaCollisionMessageHandlerTest 
{
	
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void reset()
	{
		ModelFacade.resetSingleton();
	}

	/**
	 * Tests that a AreaCollisionMessageHandler creates a command when it 
	 * receives a message.
	 */
	@Test
	public void testAreaHandleCollision() {
		reset();
		
		AreaCollisionMessageHandler handler = new AreaCollisionMessageHandler();
		AreaCollisionMessage msg = new AreaCollisionMessage(1, "");
		handler.process(msg);
		
		assertEquals(1, ModelFacade.getSingleton().queueSize());
		
		reset();
	}

}
