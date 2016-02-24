package communication.handlers;

import static org.junit.Assert.*;
import model.ClientModelFacade;

import org.junit.Before;
import org.junit.Test;

import communication.messages.QuestStateChangeMessage;
import data.QuestStateEnum;

/**
 * @author Ryan
 *
 */
public class QuestStateChangeMessageHandlerTest 
{
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void setUp()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
	}
	
	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		QuestStateChangeMessageHandler h = new QuestStateChangeMessageHandler();
		assertEquals(QuestStateChangeMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * Test that the handler messages handles the messages and creates
	 * a command
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void testMessageHandling() throws InterruptedException
	{
		QuestStateChangeMessageHandler h = new QuestStateChangeMessageHandler();
		QuestStateChangeMessage msg = new QuestStateChangeMessage(1, 2, "Quest 1", QuestStateEnum.AVAILABLE);
		h.process(msg);
		assertTrue(ClientModelFacade.getSingleton().hasCommandsPending());
		while(ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
	}

}
