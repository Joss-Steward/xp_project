package model.reports;

import static org.junit.Assert.assertEquals;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

public class LoginSuccessfulReportTest
{
	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		LoginSuccessfulReport report = new LoginSuccessfulReport(42, "LLL",56, 76);
		assertEquals(42, report.getUserID());
		assertEquals("LLL", report.getHostname());
		assertEquals(56, report.getPort());
		assertEquals(76, report.getPin());
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
