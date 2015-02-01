package edu.ship.shipsim.areaserver.model.reports;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayMock;
import edu.ship.shipsim.areaserver.model.Player;
import edu.ship.shipsim.areaserver.model.PlayerManager;
import edu.ship.shipsim.areaserver.model.reports.PlayerConnectionReport;

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
		new PlayerRowDataGatewayMock().resetData();
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
		assertEquals(PlayersForTest.JOHN.getPlayerName(), report.getPlayerName());
		assertEquals(PlayersForTest.JOHN.getAppearanceType(), report.getAppearanceType());
		assertEquals(PlayersForTest.JOHN.getPosition(), report.getPosition());
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
