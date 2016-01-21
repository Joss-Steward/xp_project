package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.ClientModelFacade;
import model.OptionsManager;
import model.PlayerManager;

import org.junit.Before;
import org.junit.Test;

import communication.messages.AdventureNotificationCompleteMessage;
import datasource.AdventuresForTest;
import datasource.PlayersForTest;
import datasource.QuestsForTest;

/**
 * Test for AdventureNotificationCompleteMessageHandler
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
		ClientModelFacade.resetSingleton();
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
	 * Test that AdventureNotificationCompleteMessageHandler creates CommandAdventureNotificationComplete
	 * @throws InterruptedException  awef
	 */
	@Test
	public void testProcessCreatesCommandAdventureNotificationComplete() throws InterruptedException
	{
		PlayerManager.getSingleton().addPlayer(1);
		AdventureNotificationCompleteMessage msg = new AdventureNotificationCompleteMessage(PlayersForTest.JOHN.getPlayerID(), QuestsForTest.ONE_BIG_QUEST.getQuestID(), AdventuresForTest.QUEST1_ADVENTURE2.getAdventureID());
		AdventureNotificationCompleteMessageHandler handler = new AdventureNotificationCompleteMessageHandler();
		handler.process(msg);
		
		assertTrue(ClientModelFacade.getSingleton().hasCommandsPending());
		while (ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		
	}

}
