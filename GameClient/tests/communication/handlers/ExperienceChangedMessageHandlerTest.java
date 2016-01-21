package communication.handlers;

import static org.junit.Assert.assertEquals;
import model.ModelFacade;

import org.junit.Before;
import org.junit.Test;

import communication.messages.ExperienceChangedMessage;
import datasource.LevelRecord;
import datasource.PlayersForTest;

/**
 * @author Ryan
 *
 */
public class ExperienceChangedMessageHandlerTest {

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
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		ExperienceChangedMessageHandler h = new ExperienceChangedMessageHandler();
		assertEquals(ExperienceChangedMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * Testing to see if a command is queued after receiving a message
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void handleExperienceChangedMessage() throws InterruptedException
	{		
		reset();
		
		LevelRecord record = new LevelRecord("Serf", 15);
		
		ExperienceChangedMessage msg = new ExperienceChangedMessage(PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getExperiencePoints(),record);
		ExperienceChangedMessageHandler h = new ExperienceChangedMessageHandler();
		h.process(msg);

		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
		while(ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		reset();
	}
}
