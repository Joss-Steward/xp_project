package communication.packers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerQuest;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.InitializeThisClientsPlayerMessage;
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
 * @author Olivia
 * @author LaVonne
 */
public class UpdatePlayerInformationMessagePackerTest
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
		UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
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
		UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
		packer.setAccumulator(stateAccumulator);

		InitializeThisClientsPlayerMessage message = (InitializeThisClientsPlayerMessage) packer.pack(report);
		ArrayList<ClientPlayerQuest> expected = report.getClientPlayerQuestList();
		ArrayList<ClientPlayerQuest> actual = message.getClientPlayerQuestList();
		assertEquals(expected.size(),actual.size());
		for (ClientPlayerQuest a:actual)
		{
			assertTrue(expected.contains(a));
		}
	}
	
	/**
	 * Tests that we can add experience pts and levelrecord to 
	 * InitializeThisClientsPlayerMessage message
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPackExperiencePtsAndLevel() throws DatabaseException
	{
		Player player = PlayerManager.getSingleton().getPlayerFromID(stateAccumulator.getPlayerID());
		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(player);
		UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
		packer.setAccumulator(stateAccumulator);

		InitializeThisClientsPlayerMessage message = (InitializeThisClientsPlayerMessage) packer.pack(report);
		assertEquals(report.getExperiencePts(), message.getExperiencePts());
		assertEquals(report.getLevel(), message.getLevel());
	}
}
