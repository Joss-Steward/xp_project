package edu.ship.shipsim.areaserver.model.reports;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;

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
		QuestNeedsFulfillmentNotificationReport report = new QuestNeedsFulfillmentNotificationReport(1,32);
		
		assertEquals(1, report.getPlayerID());
		assertEquals(32, report.getQuestID());
		
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(QuestNeedsFulfillmentNotificationReport.class).verify();
	}
}
