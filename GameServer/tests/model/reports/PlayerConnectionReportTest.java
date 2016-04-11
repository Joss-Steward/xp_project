package model.reports;

import static org.junit.Assert.*;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.reports.PlayerConnectionReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import datasource.DatabaseException;
import datasource.PlayerRowDataGatewayMock;

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
		OptionsManager.getSingleton().setTestMode(true);
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
		PlayerConnectionReport report = new PlayerConnectionReport(john.getPlayerID(),
				john.getPlayerName(), john.getAppearanceType(), john.getPlayerPosition(),
				john.getCrew(), john.getMajor());
		assertEquals(1, report.getPlayerID());
		assertEquals(PlayersForTest.JOHN.getPlayerName(), report.getPlayerName());
		assertEquals(PlayersForTest.JOHN.getAppearanceType(), report.getAppearanceType());
		assertEquals(PlayersForTest.JOHN.getPosition(), report.getPosition());
		assertEquals(PlayersForTest.JOHN.getCrew(), report.getCrew());
		assertEquals(PlayersForTest.JOHN.getMajor(), report.getMajor());
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
