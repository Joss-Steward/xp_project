package communication.handlers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import communication.messages.MovementMessage;
import data.Position;
import edu.ship.shipsim.areaserver.model.ModelFacade;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;

/**
 * 
 * @author merlin
 * 
 */
public class MovementMessageHandlerTest
{

	/**
	 * Reset the PlayerManager
	 */
	@Before
	public void reset()
	{
		PlayerManager.resetSingleton();
		ModelFacade.resetSingleton();
	}
	
	/**
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		MovementMessageHandler h = new MovementMessageHandler();
		assertEquals(MovementMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * Start with a player in a position, send that player through a movement
	 * message and ensure that the player has the new position
	 * 
	 * @throws InterruptedException
	 *             Shouldn't
	 */
	@Test
	public void updatesAPlayerPosition() throws InterruptedException
	{
		int playerID = 1;
		Position startPosition = new Position(0, 0);
		Position newPosition = new Position(1337, 1337);

		PlayerManager.getSingleton().addPlayer(1);
		Player p = PlayerManager.getSingleton().getPlayerFromID(1);
		p.setPlayerPosition(startPosition);

		assertEquals(startPosition, p.getPlayerPosition());

		MovementMessage msg = new MovementMessage(playerID, newPosition);
		MovementMessageHandler handler = new MovementMessageHandler();

		handler.process(msg);
		while (ModelFacade.getSingleton().queueSize() > 0)
		{
			Thread.sleep(100);
		}

		assertEquals(newPosition, p.getPlayerPosition());
	}
}
