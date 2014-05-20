package communication.handlers;

import static org.junit.Assert.*;
import model.ModelFacade;

import org.junit.Before;
import org.junit.Test;

import communication.messages.PlayerLeaveMessage;

/**
 * Make sure the message is handled properly
 * 
 * @author Merlin
 * 
 */
public class PlayerLeaveMessageHandlerTest
{

	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void reset()
	{
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
	}

	/**
	 * We should add the player to the player manager
	 * 
	 * @throws InterruptedException
	 *             shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		PlayerLeaveMessage msg = new PlayerLeaveMessage(1);
		PlayerLeaveMessageHandler handler = new PlayerLeaveMessageHandler();
		handler.process(msg);
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
	}

}
