package model.reports;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.reports.AdventuresNeedingNotificationReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.AdventureStateEnum;

/**
 * @author nk3668
 * Tests the creation & functionality of the 
 * AdventuresNeedingNotificationReport class
 */
public class AdventuresNeedingNotificationReportTest 
{
	/**
	 * Ensures that the report contains the correct data
	 */
	@Test
	public void testInitialization() 
	{
		ClientPlayerAdventure a = new ClientPlayerAdventure(1, "Test Adventure", 3, AdventureStateEnum.COMPLETED, true);
		ArrayList<String> adventureList = new ArrayList<String>();
		adventureList.add(a.getAdventureDescription());
		AdventuresNeedingNotificationReport report = new AdventuresNeedingNotificationReport(1, 2, 1, "Silly Adventure");
		assertEquals(1, report.getPlayerID());
		assertEquals(2, report.getQuestID());
		assertEquals(1, report.getAdventureID());
		assertEquals("Silly Adventure", report.getAdventureDescription());
	}
	
	/**
	 * Testing the equality of two instances of this class
	 */
	@Test
	public void testEqualsContract()
	{
		EqualsVerifier.forClass(AdventuresNeedingNotificationReport.class).verify();
	}
}
