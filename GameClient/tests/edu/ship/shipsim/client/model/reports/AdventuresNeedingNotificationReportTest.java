package edu.ship.shipsim.client.model.reports;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import datasource.AdventureStateEnum;

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
		ClientPlayerAdventure a = new ClientPlayerAdventure(1, "Test Adventure", AdventureStateEnum.COMPLETED, true);
		ArrayList<String> adventureList = new ArrayList<String>();
		adventureList.add(a.getAdventureDescription());
		AdventuresNeedingNotificationReport report = new AdventuresNeedingNotificationReport(adventureList);
		assertEquals(a.getAdventureDescription(), report.getAdventuresDescriptionList().get(0));
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
