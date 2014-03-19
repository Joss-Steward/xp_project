package model.reports;

import static org.junit.Assert.*;
import model.DatabaseException;
import model.Player;
import model.PlayerManager;
import model.PlayersInDB;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Merlin
 * 
 */
public class PlayerConnectionReportTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		PlayerManager.resetSingleton();
	}

	/**
	 * make sure it gets built correctly
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		PlayerConnectionReport report = new PlayerConnectionReport(john);
		assertEquals(1, report.getPlayerID());
		assertEquals(PlayersInDB.JOHN.getPlayerName(), report.getPlayerName());
		assertEquals(PlayersInDB.JOHN.getAppearanceType(), report.getAppearanceType());
		assertEquals(PlayersInDB.JOHN.getPosition(), report.getPosition());
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
