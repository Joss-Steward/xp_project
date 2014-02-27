package communication.handlers;

import static org.junit.Assert.*;
import model.Player;
import model.PlayerManager;
import model.PlayersInDB;

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
	 * We should add the player to the player manager
	 */
	@Test
	public void test()
	{
		PlayerJoinedMessage msg = new PlayerJoinedMessage(2, PlayersInDB.MERLIN.name(),
				PlayersInDB.MERLIN.getAppearanceType(), PlayersInDB.MERLIN.getPosition());
		PlayerJoinedMessageHandler handler = new PlayerJoinedMessageHandler();
		handler.process(msg);
		Player playerFromID = PlayerManager.getSingleton().getPlayerFromID(2);
		assertNotNull(playerFromID);

	}

}
