package edu.ship.shipsim.areaserver.model.reports;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.Position;
import edu.ship.shipsim.areaserver.model.reports.PlayerMovedReport;

/**
 * @author Merlin
 * 
 */
public class PlayerMovedReportTest
{

	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		PlayerMovedReport report = new PlayerMovedReport(33, "fred", new Position(3, 4), "x");
		assertEquals(33, report.getPlayerID());
		assertEquals("fred", report.getPlayerName());
		assertEquals(new Position(3, 4), report.getNewPosition());
		assertEquals("x", report.getMapName());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(PlayerMovedReport.class).verify();
	}
}
