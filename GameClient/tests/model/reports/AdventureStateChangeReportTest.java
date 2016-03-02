package model.reports;

import static org.junit.Assert.assertEquals;
import model.reports.AdventureStateChangeReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.AdventureStateEnum;
import datasource.AdventuresForTest;

/**
 * Tests the report of a change state
 * @author nk3668
 *
 */
public class AdventureStateChangeReportTest 
{

	/** 
	 * Tests the creation of the AdventureStateChangeReport 
	 */
	@Test
	public void testInitialization() 
	{
		int playerID = 42;
		int questID = AdventuresForTest.ONE.getQuestID();
		AdventureStateChangeReport report = new AdventureStateChangeReport(playerID, questID, 1, AdventuresForTest.ONE.getAdventureDescription(), AdventureStateEnum.TRIGGERED);
		assertEquals(AdventuresForTest.ONE.getAdventureID(), report.getAdventureID());
		assertEquals(AdventuresForTest.ONE.getAdventureDescription(),report.getAdventureDescription());
		assertEquals(AdventureStateEnum.TRIGGERED, report.getNewState());
		assertEquals(playerID, report.getPlayerID());
		assertEquals(questID, report.getQuestID());
	}
	
	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(AdventureStateChangeReport.class).verify();
	}
}
