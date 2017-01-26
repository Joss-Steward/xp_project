package communication.packers;

import static org.junit.Assert.*;

import java.sql.SQLException;

import model.IllegalQuestChangeException;
import model.OptionsManager;
import model.Player;
import model.PlayerConnection;
import model.PlayerManager;
import model.reports.PlayerConnectionReport;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.MapFileMessage;
import datasource.DatabaseException;

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
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setTestMode(true);
	}

	/**
	 * If we are notified about a player other than the one we are associated
	 * with, we shouldn't pack a message
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void ifThePlayerIsNotOnThisConnection() throws DatabaseException
	{
		PlayerManager.getSingleton().addPlayer(1);
		PlayerManager.getSingleton().addPlayer(2);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		Player playerFromID = PlayerManager.getSingleton().getPlayerFromID(2);
		PlayerConnectionReport report = new PlayerConnectionReport(playerFromID.getPlayerID(),
				playerFromID.getPlayerName(), playerFromID.getAppearanceType(), playerFromID.getPlayerPosition(),
				playerFromID.getCrew(), playerFromID.getMajor());
		MapFileMessagePacker packer = new MapFileMessagePacker();
		packer.setAccumulator(stateAccumulator);
		MapFileMessage msg = (MapFileMessage) packer.pack(report);
		assertNull(msg);
	}

	/**
	 * If we are notified about a player that is the one we are associated with,
	 * return null (no need to send this on)
	 * 
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void ifThePlayerIsOnThisConnection() throws DatabaseException, SQLException, IllegalQuestChangeException
	{
		OptionsManager.getSingleton().updateMapInformation("current.tmx", "", 1);
		PlayerManager.getSingleton().addPlayer(1, PlayerConnection.DEFAULT_PIN);
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);
		MapFileMessagePacker packer = new MapFileMessagePacker();
		packer.setAccumulator(stateAccumulator);
		
		Player playerFromID = PlayerManager.getSingleton().getPlayerFromID(1);
		PlayerConnectionReport report = new PlayerConnectionReport(playerFromID.getPlayerID(),
				playerFromID.getPlayerName(), playerFromID.getAppearanceType(), playerFromID.getPlayerPosition(),
				playerFromID.getCrew(), playerFromID.getMajor());
		MapFileMessage msg = (MapFileMessage) packer.pack(report);
		assertEquals(MapFileMessagePacker.DIRECTORY_PREFIX + "current.tmx", msg.getMapFileName());
		OptionsManager.resetSingleton();
	}

}
