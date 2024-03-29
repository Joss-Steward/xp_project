package datasource;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import testData.PlayersForTest;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.PlayerTableDataGateway;
import datatypes.PlayerScoreRecord;

/**
 * An abstract class that tests the table data gateways into the Adventure table
 * 
 * @author merlin
 *
 */
public abstract class PlayerTableDataGatewayTest extends DatabaseTest
{

	private PlayerTableDataGateway gateway;

	/**
	 * Make sure any static information is cleaned up between tests
	 * 
	 * @throws SQLException shouldn't
	 * @throws DatabaseException shouldn't
	 */
	@After
	public void cleanup() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * @return the gateway we should test
	 */
	public abstract PlayerTableDataGateway getGatewaySingleton();

	/**
	 * 
	 */
	@Test
	public void isASingleton()
	{
		PlayerTableDataGateway x = getGatewaySingleton();
		PlayerTableDataGateway y = getGatewaySingleton();
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveTopTenHighScores() throws DatabaseException
	{
		gateway = getGatewaySingleton();
		ArrayList<PlayerScoreRecord> results = gateway.getTopTenList();
		assertEquals(10, results.size());
		assertEquals(new PlayerScoreRecord(PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getExperiencePoints()), results.get(0));
		assertFalse(results.contains(new PlayerScoreRecord(PlayersForTest.LOSER.getPlayerName(),
				PlayersForTest.LOSER.getExperiencePoints())));

	}

}
