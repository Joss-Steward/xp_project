package datasource;

import model.DatabaseException;

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
		// TODO Auto-generated method stub
		return new PlayerLoginRowDataGatewayRDS(playerName);
	}

	/**
	 * @see datasource.PlayerLoginRowDataGatewayTest#createRowDataGateway(java.lang.String, java.lang.String)
	 */
	@Override
	PlayerLoginRowDataGateway createRowDataGateway(String playerName, String password)
			throws DatabaseException
	{
		// TODO Auto-generated method stub
		return new PlayerLoginRowDataGatewayRDS(playerName, password);
	}
}
