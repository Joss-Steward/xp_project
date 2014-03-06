package communication.packers;

import static org.junit.Assert.*;
import model.DatabaseException;
import model.PlayerManager;
import model.PlayerPin;
import model.PlayersInDB;
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
	 * If we are notified about a player, pack his information and send it to our client
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void ifThePlayerIsNotOnThisConnection() throws DatabaseException
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
		assertEquals(PlayersInDB.MERLIN.getPlayerName(), msg.getPlayerName());
		assertEquals(PlayersInDB.MERLIN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(PlayersInDB.MERLIN.getPlayerID(), msg.getPlayerID());
		assertEquals(PlayersInDB.MERLIN.getPosition(), msg.getPosition());
	}

}
