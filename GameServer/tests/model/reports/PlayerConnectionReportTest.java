package model.reports;

import static org.junit.Assert.*;
import model.Player;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;


/**
 * @author Merlin
 *
 */
public class PlayerConnectionReportTest
{

	/**
	 * make sure it gets built correctly
	 */
	@Test
	public void creation()
	{
		PlayerConnectionReport report = new PlayerConnectionReport(new Player(33, 32));
		assertEquals(33, report.getUserID());
		// TODO we need to check that the user's name gets stored correctly when we have the db
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(PlayerConnectionReport.class).verify();
	}
}
