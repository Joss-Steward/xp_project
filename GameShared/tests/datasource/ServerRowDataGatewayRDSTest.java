package datasource;



/**
 * Test the AWS RDS data source
 * @author Merlin
 *
 */
public class ServerRowDataGatewayRDSTest extends ServerRowDataGatewayTest
{
	/**
	 * @see datasource.ServerRowDataGatewayTest#createGateway(java.lang.String, java.lang.String, int)
	 */
	@Override
	ServerRowDataGateway createGateway(String mapName, String hostName, int port)
			throws DatabaseException
	{
		return new ServerRowDataGatewayRDS(mapName, hostName, port);
	}

	/**
	 * @see datasource.ServerRowDataGatewayTest#findGateway(java.lang.String)
	 */
	@Override
	ServerRowDataGateway findGateway(String mapName) throws DatabaseException
	{
		return new ServerRowDataGatewayRDS(mapName);
	}
}
