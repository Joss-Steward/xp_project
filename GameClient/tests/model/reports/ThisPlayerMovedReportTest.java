package model.reports;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.Position;

/**
 * @author Merlin
 *
 */
public class ThisPlayerMovedReportTest
{

	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		ThisPlayerMovedReport report = new ThisPlayerMovedReport(new Position(3,2));
		assertEquals(new Position(3,2), report.getNewPosition());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(ThisPlayerMovedReport.class).verify();
	}
}
