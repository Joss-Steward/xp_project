package communication.handlers;

import static org.junit.Assert.*;
import model.ModelFacade;

import org.junit.Before;
import org.junit.Test;

import communication.messages.LoginFailedMessage;

/**
 * @author Matt and Andy
 *
 */
public class LoginFailedMessageHandlerTest 
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
	 * We should add a command to the ModelFacade command queue
	 * 
	 * @throws InterruptedException
	 * 				shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		LoginFailedMessageHandler handler = new LoginFailedMessageHandler();
		LoginFailedMessage msg = new LoginFailedMessage();
		handler.process(msg);
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
	}

}
