package communication.packers;

import static org.junit.Assert.*;
import model.DatabaseException;
import model.PlayerManager;
import model.PlayerPin;
import model.reports.PlayerConnectionReport;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.MapFileMessage;

/**
 * @author Merlin
 * 
 */
public class MapFileMessagePackerTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		PlayerManager.resetSingleton();
	}

	/**
	 * If we are notified about a player other than the one we are associated
	 * with, we shouldn't pack a message
	 * @throws DatabaseException shouldn't 
	 */
	@Test
	public void ifThePlayerIsNotOnThisConnection() throws DatabaseException
	{
		PlayerManager.getSingleton().addPlayer(1, PlayerPin.DEFAULT_PIN);
		PlayerManager.getSingleton().addPlayer(2, PlayerPin.DEFAULT_PIN);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager
				.getSingleton().getPlayerFromID(2));
		MapFileMessagePacker packer = new MapFileMessagePacker();
		packer.setAccumulator(stateAccumulator);
		MapFileMessage msg = (MapFileMessage) packer.pack(report);
		assertNull(msg);
	}

	/**
	 * If we are notified about a player that is the one we are associated with,
	 * return null (no need to send this on)
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void ifThePlayerIsOnThisConnection() throws DatabaseException
	{
		PlayerManager.getSingleton().addPlayer(1, PlayerPin.DEFAULT_PIN);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager
				.getSingleton().getPlayerFromID(1));
		MapFileMessagePacker packer = new MapFileMessagePacker();
		packer.setAccumulator(stateAccumulator);
		MapFileMessage msg = (MapFileMessage) packer.pack(report);
		assertEquals("current.tmx", msg.getFileTitle());
		assertNotNull(msg.getContents());
	}

}
