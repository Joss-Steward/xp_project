package datasource;

import model.DatabaseException;


/**
 * Test our mock data source
 * @author Merlin
 *
 */
public class PlayerConnectionRowDataGatewayMockTest extends PlayerConnectionRowDataGatewayTest
{

	
	/**
	 * @see datasource.PlayerConnectionRowDataGatewayTest#createRowDataGateway(int, int, java.lang.String)
	 */
	@Override
	PlayerConnectionRowDataGateway createRowDataGateway(int playerID, int pin, String mapFileName)
	{
		return new PlayerConnectionRowDataGatewayMock(playerID, pin, mapFileName);
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGatewayTest#findRowDataGateway(int)
	 */
	@Override
	 PlayerConnectionRowDataGateway findRowDataGateway(int playerID) throws DatabaseException
	{
		return new PlayerConnectionRowDataGatewayMock(playerID);
	}

}
