package model.reports;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class LoginInitiatedReportTest
{

	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		LoginInitiatedReport report = new LoginInitiatedReport("username", "pw");
		assertEquals("username", report.getUserName());
		assertEquals("pw",report.getPassword());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(LoginInitiatedReport.class).verify();
	}
}
