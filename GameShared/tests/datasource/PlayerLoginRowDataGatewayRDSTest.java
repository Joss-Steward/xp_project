package datasource;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;



/**
 * Tests the RDS gateway
 * 
 * @author Merlin
 *
 */
public class PlayerLoginRowDataGatewayRDSTest extends PlayerLoginRowDataGatewayTest
{

	/**
	 * @see datasource.PlayerLoginRowDataGatewayTest#findRowDataGateway(java.lang.String)
	 */
	@Override
	PlayerLoginRowDataGateway findRowDataGateway(String playerName)
			throws DatabaseException
	{
		return new PlayerLoginRowDataGatewayRDS(playerName);
	}

	/**
	 * @see datasource.PlayerLoginRowDataGatewayTest#createRowDataGateway(java.lang.String, java.lang.String)
	 */
	@Override
	PlayerLoginRowDataGateway createRowDataGateway(String playerName, String password)
			throws DatabaseException
	{
		return new PlayerLoginRowDataGatewayRDS(playerName, password);
	}

	/**
	 * 
	 * @see datasource.PlayerLoginRowDataGatewayTest#findRowDataGateway(int)
	 */
	@Override
	PlayerLoginRowDataGateway findRowDataGateway(int playerID) throws DatabaseException
	{
		return new PlayerLoginRowDataGatewayRDS(playerID);
	}
	

	/**
	 * check that we can retrieve all of the names of the players in the database
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canGetAllPlayerNames() throws DatabaseException
	{
		List<String> names = PlayerLoginRowDataGatewayRDS.getAllPlayerNames();
		for( PlayersForTest player :PlayersForTest.values())
		{
			assertTrue(names.contains(player.getPlayerName()));
		}
	}
}
