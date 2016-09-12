package model.reports;

import static org.junit.Assert.*;
import model.reports.PlayerMovedReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import datatypes.Position;

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
