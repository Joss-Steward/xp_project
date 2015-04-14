package edu.ship.shipsim.areaserver.model.reports;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.QuestStateEnum;
import edu.ship.shipsim.areaserver.datasource.QuestsForTest;

/**
 * @author Merlin
 * 
 */
public class QuestNeedsFulfillmentNotificationReportTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
			}

	/**
	 * make sure it gets built correctly
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		QuestStateChangeReport report = new QuestStateChangeReport(1,QuestsForTest.ONE_BIG_QUEST.getQuestID(), 
				QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), QuestStateEnum.FINISHED);
		
		assertEquals(1, report.getPlayerID());
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestID(), report.getQuestID());
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(),report.getQuestDescription());
		assertEquals(QuestStateEnum.FINISHED, report.getNewState());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(QuestStateChangeReport.class).verify();
	}
}
