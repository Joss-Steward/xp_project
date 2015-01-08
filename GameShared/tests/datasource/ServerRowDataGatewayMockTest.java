package datasource;

/**
 * Test our mock behavior
 * @author Merlin
 *
 */
public class ServerRowDataGatewayMockTest extends ServerRowDataGatewayTest
{

	ServerRowDataGateway createGateway()
	{
		ServerRowDataGatewayMock x = new ServerRowDataGatewayMock();
		return x;
	}
}
