package communication.handlers;

import static org.junit.Assert.assertEquals;
import model.ClientModelFacade;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import communication.messages.KnowledgeChangedMessage;

/**
 * @author Matthew Croft
 * @author Emily Maust
 *
 */
public class KnowledgeChangedMessageHandlerTest {
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void reset()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
	}
	
	/**
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		KnowledgeChangedMessageHandler h = new KnowledgeChangedMessageHandler();
		assertEquals(KnowledgeChangedMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * Testing to see if a command is queued after receiving a message
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void handleExperienceChangedMessage() throws InterruptedException
	{	
		//TODO: test is failing!
		int oldScore = PlayersForTest.JOHN.getKnowledgeScore();
		
		KnowledgeChangedMessage msg = new KnowledgeChangedMessage(PlayersForTest.JOHN.getPlayerID(), oldScore + 5);
		KnowledgeChangedMessageHandler h = new KnowledgeChangedMessageHandler();
		h.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		int length = ClientModelFacade.getSingleton().getCommandQueueLength();
		assertEquals(1, length);
		
		while(ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
	}
}
