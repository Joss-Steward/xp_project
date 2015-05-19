package edu.ship.shipsim.client.model.reports;

import static org.junit.Assert.*;

import org.junit.Test;

import datasource.QuestStateEnum;

/**
 * @author Ryan
 *
 */
public class QuestStateChangeReportTest 
{

	/**
	 * Test creating a report and test its getters
	 */
	@Test
	public void testInitialization() 
	{
		QuestStateChangeReport r = new QuestStateChangeReport(1, "Big Quest", QuestStateEnum.TRIGGERED);
		
		assertEquals(1, r.getQuestID());
		assertEquals("Big Quest", r.getQuestDescription());
		assertEquals(QuestStateEnum.TRIGGERED, r.getNewState());
	}

}
