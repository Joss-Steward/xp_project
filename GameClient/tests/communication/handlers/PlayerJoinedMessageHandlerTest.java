package communication.handlers;

import static org.junit.Assert.*;
import model.PlayerManager;

import org.junit.Test;

import communication.messages.PlayerJoinedMessage;

/**
 * Make sure the message is handled properly
 * @author Merlin
 *
 */
public class PlayerJoinedMessageHandlerTest
{

	/**
	 * We should add the player to the player manager
	 */
	@Test
	public void test()
	{
		PlayerJoinedMessage msg = new PlayerJoinedMessage("John");
		PlayerJoinedMessageHandler handler = new PlayerJoinedMessageHandler();
		handler.process(msg);
		assertNotNull(PlayerManager.getSingleton().getPlayerNamed("John"));
	}

}
