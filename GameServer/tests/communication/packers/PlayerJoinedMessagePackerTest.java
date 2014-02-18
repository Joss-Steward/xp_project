package communication.packers;

import static org.junit.Assert.*;
import model.PlayerManager;
import model.PlayerPin;
import model.reports.PlayerConnectionReport;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.PlayerJoinedMessage;

/**
 * @author Merlin
 * 
 */
public class PlayerJoinedMessagePackerTest
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
		PlayerManager.getSingleton().addPlayer(1, PlayerPin.DEFAULT_PIN);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);
		PlayerManager.getSingleton().addPlayer(2, PlayerPin.DEFAULT_PIN);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager.getSingleton()
				.getPlayerFromID(2));
		PlayerJoinedMessagePacker packer = new PlayerJoinedMessagePacker();
		packer.setAccumulator(stateAccumulator);
		PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
		assertEquals("Merlin", msg.getPlayerName());
	}

	/**
	 * If we are notified about a player that is the one we are associated with,
	 * return null (no need to send this on)
	 */
	@Test
	public void ifThePlayerIsOnThisConnection()
	{
		PlayerManager.getSingleton().addPlayer(1, PlayerPin.DEFAULT_PIN);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager.getSingleton()
				.getPlayerFromID(1));
		PlayerJoinedMessagePacker packer = new PlayerJoinedMessagePacker();
		packer.setAccumulator(stateAccumulator);
		PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
		assertNull(msg);
	}

}
