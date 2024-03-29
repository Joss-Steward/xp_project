package model.reports;

import static org.junit.Assert.*;
import model.reports.QuestStateChangeReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import testData.QuestsForTest;
import datasource.DatabaseException;
import datatypes.QuestStateEnum;

/**
 * @author Merlin
 * 
 */
public class QuestStateChangeReportTest
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
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		QuestStateChangeReport report = new QuestStateChangeReport(1, QuestsForTest.ONE_BIG_QUEST.getQuestID(),
				QuestsForTest.ONE_BIG_QUEST.getQuestTitle(), QuestsForTest.ONE_BIG_QUEST.getQuestDescription(),
				QuestStateEnum.COMPLETED);

		assertEquals(1, report.getPlayerID());
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestID(), report.getQuestID());
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), report.getQuestDescription());
		assertEquals(QuestStateEnum.COMPLETED, report.getNewState());
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
