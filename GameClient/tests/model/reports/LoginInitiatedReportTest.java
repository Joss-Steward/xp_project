package model.reports;

import static org.junit.Assert.*;
import model.reports.LoginInitiatedReport;
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
		LoginInitiatedReport report = new LoginInitiatedReport("playername", "pw");
		assertEquals("playername", report.getPlayerName());
		assertEquals("pw", report.getPassword());
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
