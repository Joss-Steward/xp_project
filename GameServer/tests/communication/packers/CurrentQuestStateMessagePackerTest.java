package communication.packers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerQuest;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.CurrentQuestStateMessage;
import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.QuestManager;
import edu.ship.shipsim.areaserver.model.reports.UpdatePlayerInformationReport;

/**
 * 
 * @author Andrew
 * @author Steve
 * @author Matt
 * 
 */
public class CurrentQuestStateMessagePackerTest
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
		QuestManager.resetSingleton();

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
		CurrentQuestStateMessagePacker packer = new CurrentQuestStateMessagePacker();
		assertEquals(UpdatePlayerInformationReport.class, packer.getReportTypeWePack());
	}

	/**
	 * the message should contain the appropriate quests and adventures
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPackedObjectIsCurrentPlayer() throws DatabaseException
	{
		Player player = PlayerManager.getSingleton().getPlayerFromID(stateAccumulator.getPlayerID());
		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(player);
		CurrentQuestStateMessagePacker packer = new CurrentQuestStateMessagePacker();
		packer.setAccumulator(stateAccumulator);

		CurrentQuestStateMessage message = (CurrentQuestStateMessage) packer.pack(report);
		ArrayList<ClientPlayerQuest> expected = report.getClientPlayerQuestList();
		ArrayList<ClientPlayerQuest> actual = message.getClientPlayerQuestList();
		assertEquals(expected.size(),actual.size());
		for (ClientPlayerQuest a:actual)
		{
			assertTrue(expected.contains(a));
		}
	}

}
