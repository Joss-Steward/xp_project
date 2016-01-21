package model.reports;

import static org.junit.Assert.*;
import model.reports.AdventureNotifcationCompleteReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * Test for AdventureNotifcationCompleteReport
 * @author Ryan
 *
 */
public class AdventureNotifcationCompleteReportTest 
{

	/**
	 * Test init and getters for report
	 */
	@Test
	public void testInitialization() 
	{
		AdventureNotifcationCompleteReport report = new AdventureNotifcationCompleteReport(1, 2, 3);
		
		assertEquals(1, report.getPlayerID());
		assertEquals(2, report.getQuestID());
		assertEquals(3, report.getAdventureID());
	}
	
	/**
	 * Testing the equality of two instances of this class
	 */
	@Test
	public void testEqualsContract()
	{
		EqualsVerifier.forClass(AdventureNotifcationCompleteReport.class).verify();
	}
}
