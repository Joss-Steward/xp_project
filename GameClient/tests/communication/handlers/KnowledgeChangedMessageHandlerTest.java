package communication.handlers;

import static org.junit.Assert.assertEquals;
import model.ClientModelFacade;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import communication.messages.KnowledgeChangedMessage;
import datasource.LevelRecord;

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
		ClientModelFacade.getSingleton(true, true);
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
		//TODO: throwing an error?
		reset();
		
		LevelRecord record = new LevelRecord("Serf", 15);
		
		KnowledgeChangedMessage msg = new KnowledgeChangedMessage(PlayersForTest.JOHN.getPlayerID(), record,PlayersForTest.JOHN.getKnowledgeScore());
		KnowledgeChangedMessageHandler h = new KnowledgeChangedMessageHandler();
		h.process(msg);

		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		while(ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		reset();
	}
}
