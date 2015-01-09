package datasource;

import model.DatabaseException;


/**
 * Test our RDS data source
 * @author Merlin
 *
 */
public class PlayerConnectionRowDataGatewayRDSTest extends PlayerConnectionRowDataGatewayTest
{

	
	/**
	 * @see datasource.PlayerConnectionRowDataGatewayTest#createRowDataGateway(int, int, java.lang.String)
	 */
	@Override
	PlayerConnectionRowDataGateway createRowDataGateway(int playerID, int pin, String mapFileName) throws DatabaseException
	{
		return new PlayerConnectionRowDataGatewayRDS(playerID, pin, mapFileName);
	}

	
	/**
	 * @see datasource.PlayerConnectionRowDataGatewayTest#findRowDataGateway(int)
	 */
	@Override
	PlayerConnectionRowDataGateway findRowDataGateway(int playerID) throws DatabaseException
	{
		return new PlayerConnectionRowDataGatewayRDS(playerID);
	}

}
