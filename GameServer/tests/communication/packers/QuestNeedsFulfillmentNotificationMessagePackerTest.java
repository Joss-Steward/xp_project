package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.QuestNeedsFulfillmentNotificationMessage;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.QuestsForTest;
import edu.ship.shipsim.areaserver.model.QuestManager;
import edu.ship.shipsim.areaserver.model.reports.QuestNeedsFulfillmentNotificationReport;

/**
 * 
 * @author Andrew
 * @author Steve
 * @author Matt
 * @author Olivia
 * @author LaVonne
 */
public class QuestNeedsFulfillmentNotificationMessagePackerTest
{
	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton(true);
		QuestManager.resetSingleton();

	}

	/**
	 * Test that we pack a PlayerMovedReport
	 */
	@Test
	public void testReportTypeWePack()
	{
		QuestNeedsFulfillmentNotificationMessagePacker packer = new QuestNeedsFulfillmentNotificationMessagePacker();
		assertEquals(QuestNeedsFulfillmentNotificationReport.class,
				packer.getReportTypeWePack());
	}

	/**
	 * If we are notified about a player other than the one we are associated
	 * with, we shouldn't pack a message
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void ifThePlayerIsNotOnThisConnection() throws DatabaseException
	{
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);

		QuestNeedsFulfillmentNotificationReport report = new QuestNeedsFulfillmentNotificationReport(
				2, QuestsForTest.ONE_BIG_QUEST.getQuestID(), QuestsForTest.ONE_BIG_QUEST.getQuestDescription());
		QuestNeedsFulfillmentNotificationMessagePacker packer = new QuestNeedsFulfillmentNotificationMessagePacker();
		packer.setAccumulator(stateAccumulator);
		
		QuestNeedsFulfillmentNotificationMessage msg = (QuestNeedsFulfillmentNotificationMessage) packer.pack(report);
		assertNull(msg);
	}
	/**
	 * If the msg is to the player we are packing, the message should contain the details of the quest
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testPackedMessageIsBuiltCorrectly() throws DatabaseException
	{
		StateAccumulator stateAccumulator = new StateAccumulator(null);
		stateAccumulator.setPlayerId(1);
		
		QuestNeedsFulfillmentNotificationReport report = new QuestNeedsFulfillmentNotificationReport(
				stateAccumulator.getPlayerID(), QuestsForTest.ONE_BIG_QUEST.getQuestID(), QuestsForTest.ONE_BIG_QUEST.getQuestDescription());
		QuestNeedsFulfillmentNotificationMessagePacker packer = new QuestNeedsFulfillmentNotificationMessagePacker();
		packer.setAccumulator(stateAccumulator);
		
		QuestNeedsFulfillmentNotificationMessage msg = (QuestNeedsFulfillmentNotificationMessage) packer.pack(report);
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestID(), msg.getQuestID());
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(),msg.getQuestDescription());

	}
}
