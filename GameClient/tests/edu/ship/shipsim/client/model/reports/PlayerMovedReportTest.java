package edu.ship.shipsim.client.model.reports;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.Position;
import edu.ship.shipsim.client.model.reports.PlayerMovedReport;

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
		PlayerMovedReport report = new PlayerMovedReport(1, new Position(3, 2));
		assertEquals(new Position(3, 2), report.getNewPosition());
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
