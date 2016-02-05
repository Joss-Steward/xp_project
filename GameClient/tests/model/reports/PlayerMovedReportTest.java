package model.reports;

import static org.junit.Assert.*;
import model.reports.ThisClientsPlayerMovedReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.Position;

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
		ThisClientsPlayerMovedReport report = new ThisClientsPlayerMovedReport(1, new Position(3, 2));
		assertEquals(new Position(3, 2), report.getNewPosition());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(ThisClientsPlayerMovedReport.class).verify();
	}
}
