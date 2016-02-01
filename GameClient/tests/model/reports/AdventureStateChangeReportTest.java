package model.reports;

import static org.junit.Assert.assertEquals;
import model.reports.AdventureStateChangeReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import datasource.AdventureStateEnum;
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
		AdventureStateChangeReport report = new AdventureStateChangeReport(1, AdventuresForTest.ONE.getAdventureDescription(), AdventureStateEnum.TRIGGERED);
		assertEquals(AdventuresForTest.ONE.getAdventureID(), report.getadventureID());
		assertEquals(AdventuresForTest.ONE.getAdventureDescription(),report.getAdventureDescription());
		assertEquals(AdventureStateEnum.TRIGGERED, report.getNewState());
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
