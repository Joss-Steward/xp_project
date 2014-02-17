package model.reports;

import static org.junit.Assert.*;
import model.DatabaseException;
import model.Player;
import model.PlayerPin;
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
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		PlayerConnectionReport report = new PlayerConnectionReport(new Player(1,
				PlayerPin.DEFAULT_PIN));
		assertEquals(1, report.getPlayerID());
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
