package model.reports;

import static org.junit.Assert.*;
import model.DatabaseException;
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
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		PlayerConnectionReport report = new PlayerConnectionReport(new Player(1,33));
		assertEquals(1, report.getUserID());
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
