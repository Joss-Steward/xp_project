package communication.packers;

import static org.junit.Assert.*;
import model.ModelFacade;
import model.OptionsManager;
import model.PlayerManager;
import model.QuestManager;
import model.reports.TeleportOnQuestCompletionReport;

import org.junit.Before;
import org.junit.Test;

import testData.AdventuresForTest;
import communication.StateAccumulator;
import communication.messages.TeleportationContinuationMessage;
import data.GameLocation;
import datasource.DatabaseException;

/**
 * makes sure TeleportOnQuestCompletionPacker works well
 * 
 * @author Abdul, Chris Hersh, Zach Thompson
 *
 */
public class TeleportOnQuestCompletionPackerTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setTestMode(true);
		QuestManager.resetSingleton();

		PlayerManager.resetSingleton();
		ModelFacade.resetSingleton();

	}

	/**
	 * Test that we pack a PlayerMovedReport
	 */
	@Test
	public void testReportTypeWePack()
	{
		TeleportOnQuestCompletionPacker packer = new TeleportOnQuestCompletionPacker();
		assertEquals(TeleportOnQuestCompletionReport.class, packer.getReportTypesWePack().get(0));
		assertEquals(1, packer.getReportTypesWePack().size());
	}

	/**
	 * If the msg is to the player we are packing, the message should contain
	 * the details of the quest
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPackedMessageIsBuiltCorrectly() throws DatabaseException
	{
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		String host = "hostname";
		int port = 22;
		GameLocation gl = new GameLocation("current.tmx", null);

		TeleportOnQuestCompletionReport report = new TeleportOnQuestCompletionReport(1,
				AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), gl, host, port);

		TeleportOnQuestCompletionPacker packer = new TeleportOnQuestCompletionPacker();
		packer.setAccumulator(stateAccumulator);

		TeleportationContinuationMessage msg = (TeleportationContinuationMessage) packer.pack(report);
		assertEquals(gl.getMapName(), msg.getMapName());
		assertEquals(host, msg.getHostName());
		assertEquals(port, msg.getPortNumber());
		assertEquals(1, msg.getPlayerID());

	}

}
