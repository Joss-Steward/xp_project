package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.ModelFacade;
import model.OptionsManager;
import model.PlayerManager;

import org.junit.Before;
import org.junit.Test;

import testData.AdventuresForTest;
import testData.PlayersForTest;
import testData.QuestsForTest;
import communication.messages.AdventureNotificationCompleteMessage;

/**
 * Test for AdventureNotificationCompleteMessageHandler
 * 
 * @author Ryan
 *
 */
public class AdventureNotificationCompleteMessageHandlerTest
{

	/**
	 * Reset the PlayerManager
	 */
	@Before
	public void reset()
	{
		PlayerManager.resetSingleton();
		ModelFacade.resetSingleton();
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setTestMode(true);
	}

	/**
	 * Test what message type we handle
	 */
	@Test
	public void testMessageWeHandle()
	{
		AdventureNotificationCompleteMessageHandler h = new AdventureNotificationCompleteMessageHandler();

		assertEquals(AdventureNotificationCompleteMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * Test that AdventureNotificationCompleteMessageHandler creates
	 * CommandAdventureNotificationComplete
	 * 
	 * @throws InterruptedException awef
	 */
	@Test
	public void testProcessCreatesCommandAdventureNotificationComplete() throws InterruptedException
	{
		PlayerManager.getSingleton().addPlayer(1);
		AdventureNotificationCompleteMessage msg = new AdventureNotificationCompleteMessage(
				PlayersForTest.JOHN.getPlayerID(), QuestsForTest.ONE_BIG_QUEST.getQuestID(),
				AdventuresForTest.QUEST1_ADVENTURE2.getAdventureID());
		AdventureNotificationCompleteMessageHandler handler = new AdventureNotificationCompleteMessageHandler();
		handler.process(msg);

		assertTrue(ModelFacade.getSingleton().hasCommandsPending());
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}

	}

}
