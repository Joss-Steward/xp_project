package communication.packers;

import static org.junit.Assert.assertEquals;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.AdventureStateChangeMessage;
import datasource.AdventureStateEnum;
import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;
import edu.ship.shipsim.areaserver.model.QuestManager;
import edu.ship.shipsim.areaserver.model.reports.AdventureStateChangeReport;

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
		assertEquals(AdventureStateChangeReport.class, packer.getReportTypeWePack());
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
				stateAccumulator.getPlayerID(), AdventuresForTest.ONE.getQuestID(), AdventuresForTest.ONE.getAdventureID(), AdventuresForTest.ONE.getAdventureDescription(), AdventureStateEnum.TRIGGERED);
		AdventureStateChangeMessagePacker packer = new AdventureStateChangeMessagePacker();
		packer.setAccumulator(stateAccumulator);
		
		AdventureStateChangeMessage msg = (AdventureStateChangeMessage) packer.pack(report);
		assertEquals(AdventuresForTest.ONE.getAdventureID(), msg.getAdventureID());
		assertEquals(AdventuresForTest.ONE.getAdventureDescription(),msg.getAdventureDescription());
		assertEquals(AdventureStateEnum.TRIGGERED, msg.getNewState());

	}

}
