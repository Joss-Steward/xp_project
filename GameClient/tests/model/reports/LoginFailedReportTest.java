package model.reports;

import static org.junit.Assert.*;
import model.reports.LoginFailedReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * @author mk3969
 * 
 * Tests to see if LoginFailedReport is working properly. 
 *
 */
public class LoginFailedReportTest 
{

	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		LoginFailedReport report = new LoginFailedReport("Login Failed");
		assertEquals("Login Failed", report.toString());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(LoginFailedReport.class).verify();
	}

}
