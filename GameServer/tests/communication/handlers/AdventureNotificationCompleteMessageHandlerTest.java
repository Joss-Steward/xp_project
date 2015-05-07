package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import communication.messages.AdventureNotificationCompleteMessage;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;
import edu.ship.shipsim.areaserver.datasource.QuestsForTest;
import edu.ship.shipsim.areaserver.model.ModelFacade;
import edu.ship.shipsim.areaserver.model.PlayerManager;

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
	 * Test that AdventureNotificationCompleteMessageHandler creates CommandAdventureNotificationComplete
	 * @throws InterruptedException  awef
	 */
	@Test
	public void testProcessCreatesCommandAdventureNotificationComplete() throws InterruptedException
	{
		PlayerManager.getSingleton().addPlayer(1);
		AdventureNotificationCompleteMessage msg = new AdventureNotificationCompleteMessage(PlayersForTest.JOHN.getPlayerID(), QuestsForTest.ONE_BIG_QUEST.getQuestID(), AdventuresForTest.TWO.getAdventureID());
		AdventureNotificationCompleteMessageHandler handler = new AdventureNotificationCompleteMessageHandler();
		handler.process(msg);
		
		assertTrue(ModelFacade.getSingleton().hasCommandsPending());
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		
	}

}
