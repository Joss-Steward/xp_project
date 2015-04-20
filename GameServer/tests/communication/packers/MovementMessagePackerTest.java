package communication.packers;

import static org.junit.Assert.*;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.MovementMessage;
import data.Position;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.reports.PlayerMovedReport;

/**
 * 
 * @author Andrew
 * @author Steve
 * @author Matt
 * 
 */
public class MovementMessagePackerTest
{
	private StateAccumulator stateAccumulator;

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton(true);
		PlayerManager.resetSingleton();

		PlayerManager.getSingleton().addPlayer(1);
		stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);
		
		
	}

	/**
	 * Test that we pack a PlayerMovedReport
	 */
	@Test
	public void testReportTypeWePack()
	{
		MovementMessagePacker packer = new MovementMessagePacker();
		assertEquals(PlayerMovedReport.class, packer.getReportTypeWePack());
	}

	/**
	 * Given a PlayerMovedReport for the current player, the message should be
	 * null
	 */
	@Test
	public void testPackedObjectIsCurrentPlayer()
	{
		Position position = new Position(1, 2);
		PlayerMovedReport report = new PlayerMovedReport(stateAccumulator.getPlayerID(),
				"fred", position, "mapName");
		MovementMessagePacker packer = new MovementMessagePacker();
		packer.setAccumulator(stateAccumulator);

		MovementMessage message = (MovementMessage) packer.pack(report);
		assertNull(message);
	}

	/**
	 * Given a PlayerMovedReport for a player other than the client, ensure that
	 * the MovementMessage is what we expect
	 */
	@Test
	public void testPackedObjectNotCurrentPlayer()
	{
		Position position = new Position(1, 2);
		PlayerMovedReport report = new PlayerMovedReport(-1, "fred", position, "mapName");
		MovementMessagePacker packer = new MovementMessagePacker();
		packer.setAccumulator(stateAccumulator);

		MovementMessage message = (MovementMessage) packer.pack(report);
		assertEquals(-1, message.getPlayerID());
		assertEquals(position, message.getPosition());
	}
}
