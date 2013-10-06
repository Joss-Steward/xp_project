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
	 * with, pack the right message
	 */
	@Test
	public void ifThePlayerIsNotOnThisConnection()
	{
		PlayerManager.getSingleton().addPlayer(42, 1234);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerUserId(43);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager.getSingleton()
				.getPlayerFromID(42));
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
		PlayerManager.getSingleton().addPlayer(42, 1234);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerUserId(42);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager.getSingleton()
				.getPlayerFromID(42));
		MapFileMessagePacker packer = new MapFileMessagePacker();
		packer.setAccumulator(stateAccumulator);
		MapFileMessage msg = (MapFileMessage) packer.pack(report);
		assertEquals("current.tmx",msg.getFileTitle());
		assertNotNull(msg.getContents());
	}

}
