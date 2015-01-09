package datasource;

import model.DatabaseException;

/**
 * Test our mock behavior
 * @author Merlin
 *
 */
public class ServerRowDataGatewayMockTest extends ServerRowDataGatewayTest
{

	/**
	 * @see datasource.ServerRowDataGatewayTest#createGateway(java.lang.String, java.lang.String, int)
	 */
	@Override
	ServerRowDataGateway createGateway(String mapName, String hostName, int port) throws DatabaseException
	{
		return new ServerRowDataGatewayMock(mapName, hostName, port);
	}

	/**
	 * @see datasource.ServerRowDataGatewayTest#findGateway(java.lang.String)
	 */
	@Override
	ServerRowDataGateway findGateway(String mapName) throws DatabaseException
	{
		return new ServerRowDataGatewayMock(mapName);
	}
}
