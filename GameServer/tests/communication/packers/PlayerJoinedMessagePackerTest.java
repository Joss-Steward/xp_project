package communication.packers;

import static org.junit.Assert.*;
import model.DatabaseException;
import model.PlayerConnection;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.PlayerJoinedMessage;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.reports.PlayerConnectionReport;

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
	 * If we are notified about a player, pack his information and send it to
	 * our client
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void ifThePlayerIsNotOnThisConnection() throws DatabaseException
	{
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID(), PlayerConnection.DEFAULT_PIN);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
		PlayerManager.getSingleton().addPlayer(PlayersForTest.JOHN.getPlayerID(), PlayerConnection.DEFAULT_PIN);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager
				.getSingleton().getPlayerFromID(PlayersForTest.JOHN.getPlayerID()));
		PlayerJoinedMessagePacker packer = new PlayerJoinedMessagePacker();
		packer.setAccumulator(stateAccumulator);
		PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
		assertEquals(PlayersForTest.JOHN.getPlayerName(), msg.getPlayerName());
		assertEquals(PlayersForTest.JOHN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(PlayersForTest.JOHN.getPlayerID(), msg.getPlayerID());
		assertEquals(PlayersForTest.JOHN.getPosition(), msg.getPosition());
	}

}
