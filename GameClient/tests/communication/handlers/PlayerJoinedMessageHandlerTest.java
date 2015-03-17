package communication.handlers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import communication.messages.PlayerJoinedMessage;
import datasource.PlayersForTest;
import edu.ship.shipsim.client.model.ModelFacade;

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
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
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
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
	}

}
