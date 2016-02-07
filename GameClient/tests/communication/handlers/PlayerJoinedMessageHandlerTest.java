package communication.handlers;

import static org.junit.Assert.*;
import model.ClientModelFacade;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import communication.messages.PlayerJoinedMessage;

/**
 * Make sure the message is handled properly
 * 
 * @author Merlin
 * 
 */
public class PlayerJoinedMessageHandlerTest
{

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
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		PlayerJoinedMessageHandler h = new PlayerJoinedMessageHandler();
		assertEquals(PlayerJoinedMessage.class, h.getMessageTypeWeHandle());
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
		PlayerJoinedMessage msg = new PlayerJoinedMessage(2, PlayersForTest.MERLIN.name(),
				PlayersForTest.MERLIN.getAppearanceType(), PlayersForTest.MERLIN.getPosition());
		PlayerJoinedMessageHandler handler = new PlayerJoinedMessageHandler();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		while(ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
	}

}
