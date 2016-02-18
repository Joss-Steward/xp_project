package communication.packers;

import static org.junit.Assert.assertEquals;
import model.OptionsManager;
import model.QuestManager;
import model.reports.AdventureStateChangeReport;

import org.junit.Before;
import org.junit.Test;

import testData.AdventuresForTest;
import communication.StateAccumulator;
import communication.messages.AdventureStateChangeMessage;
import data.AdventureStateEnum;
import datasource.DatabaseException;

/**
 * @author Ryan
 *
 */
public class AdventureStateChangeMessagePackerTest 
{
	
	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setTestMode(true);
		QuestManager.resetSingleton();

	}

	/**
	 * Test that we pack a Adventure State Change Report
	 */
	@Test
	public void testReportTypeWePack()
	{
		AdventureStateChangeMessagePacker packer = new AdventureStateChangeMessagePacker();
		assertEquals(AdventureStateChangeReport.class, packer.getReportTypesWePack().get(0));
	}
	
	/**
	 * If the msg is to the player we are packing, the message should contain the details of the quest 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPackedMessageIsBuiltCorrectly() throws DatabaseException
	{
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);
		
		AdventureStateChangeReport report = new AdventureStateChangeReport(
				stateAccumulator.getPlayerID(), AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(), AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(), AdventureStateEnum.TRIGGERED);
		AdventureStateChangeMessagePacker packer = new AdventureStateChangeMessagePacker();
		packer.setAccumulator(stateAccumulator);
		
		AdventureStateChangeMessage msg = (AdventureStateChangeMessage) packer.pack(report);
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(), msg.getAdventureID());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),msg.getAdventureDescription());
		assertEquals(AdventureStateEnum.TRIGGERED, msg.getNewState());

	}

}
