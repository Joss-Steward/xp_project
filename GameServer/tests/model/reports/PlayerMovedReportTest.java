package model.reports;

import static org.junit.Assert.*;
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
		PlayerMovedReport report = new PlayerMovedReport(33, new Position(3,4));
		assertEquals(33, report.getPlayerID());
		assertEquals(new Position(3,4), report.getNewPosition());
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
