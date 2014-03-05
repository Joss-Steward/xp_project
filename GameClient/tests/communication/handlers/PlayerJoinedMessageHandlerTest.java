package communication.handlers;

import static org.junit.Assert.*;
import model.ModelFacade;
import model.PlayersInDB;

import org.junit.Before;
import org.junit.Test;

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
		ModelFacade.resetSingleton(true, true);
	}
	
	/**
	 * We should add the player to the player manager
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		PlayerJoinedMessage msg = new PlayerJoinedMessage(2, PlayersInDB.MERLIN.name(),
				PlayersInDB.MERLIN.getAppearanceType(), PlayersInDB.MERLIN.getPosition());
		PlayerJoinedMessageHandler handler = new PlayerJoinedMessageHandler();
		handler.process(msg);
		assertEquals(1, ModelFacade.getSingleton(true, true).getCommandQueueLength());
	}

}
