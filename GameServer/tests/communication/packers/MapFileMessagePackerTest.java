package communication.packers;

import static org.junit.Assert.*;
import model.PlayerManager;
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
	 */
	@Test
	public void ifThePlayerIsNotOnThisConnection()
	{
		PlayerManager.getSingleton().addPlayer(2, 1234);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager.getSingleton()
				.getPlayerFromID(2));
		MapFileMessagePacker packer = new MapFileMessagePacker();
		packer.setAccumulator(stateAccumulator);
		MapFileMessage msg = (MapFileMessage) packer.pack(report);
		assertNull(msg);
	}
	
	/**
	 * If we are notified about a player that is the one we are associated
	 * with, return null (no need to send this on)
	 */
	@Test
	public void ifThePlayerIsOnThisConnection()
	{
		PlayerManager.getSingleton().addPlayer(1, 1234);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager.getSingleton()
				.getPlayerFromID(1));
		MapFileMessagePacker packer = new MapFileMessagePacker();
		packer.setAccumulator(stateAccumulator);
		MapFileMessage msg = (MapFileMessage) packer.pack(report);
		assertEquals("current.tmx",msg.getFileTitle());
		assertNotNull(msg.getContents());
	}

}
