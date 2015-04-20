package communication.handlers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import communication.messages.QuestStateChangeMessage;
import datasource.QuestStateEnum;
import edu.ship.shipsim.client.model.ModelFacade;

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
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
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
	 */
	@Test
	public void testMessageHandling()
	{
		QuestStateChangeMessageHandler h = new QuestStateChangeMessageHandler();
		QuestStateChangeMessage msg = new QuestStateChangeMessage(1, 2, "Quest 1", QuestStateEnum.AVAILABLE);
		
		h.process(msg);
		
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
	}

}
