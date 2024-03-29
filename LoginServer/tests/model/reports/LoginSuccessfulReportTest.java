package model.reports;

import static org.junit.Assert.assertEquals;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * 
 * @author Merlin
 * 
 */
public class LoginSuccessfulReportTest
{
	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		LoginSuccessfulReport report = new LoginSuccessfulReport(42, "LLL", 56, 0.76);
		assertEquals(42, report.getPlayerID());
		assertEquals("LLL", report.getHostname());
		assertEquals(56, report.getPort());
		assertEquals(0.76, report.getPin(), 0.00001);
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(LoginSuccessfulReport.class).verify();
	}
}
