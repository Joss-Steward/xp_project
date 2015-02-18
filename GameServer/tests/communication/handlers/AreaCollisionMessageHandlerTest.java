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

	@Test
	public void testAreaHandleCollision() {
		//Add to all tests
		reset();
		
		AreaCollisionMessageHandler handler = new AreaCollisionMessageHandler();
		AreaCollisionMessage msg = new AreaCollisionMessage(1, "");
		handler.process(msg);
		
		assertEquals(1,ModelFacade.getSingleton().queueSize());
		
		reset();
	}

}
