package communication.packers;

import static org.junit.Assert.*;
import model.DatabaseException;
import model.PlayerConnection;
import model.PlayersInDB;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.PlayerJoinedMessage;
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
		PlayerManager.getSingleton().addPlayer(PlayersInDB.MERLIN.getPlayerID(), PlayerConnection.DEFAULT_PIN);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(PlayersInDB.MERLIN.getPlayerID());
		PlayerManager.getSingleton().addPlayer(PlayersInDB.JOHN.getPlayerID(), PlayerConnection.DEFAULT_PIN);

		PlayerConnectionReport report = new PlayerConnectionReport(PlayerManager
				.getSingleton().getPlayerFromID(PlayersInDB.JOHN.getPlayerID()));
		PlayerJoinedMessagePacker packer = new PlayerJoinedMessagePacker();
		packer.setAccumulator(stateAccumulator);
		PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
		assertEquals(PlayersInDB.JOHN.getPlayerName(), msg.getPlayerName());
		assertEquals(PlayersInDB.JOHN.getAppearanceType(), msg.getAppearanceType());
		assertEquals(PlayersInDB.JOHN.getPlayerID(), msg.getPlayerID());
		assertEquals(PlayersInDB.JOHN.getPosition(), msg.getPosition());
	}

}
