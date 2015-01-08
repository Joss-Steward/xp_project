package datasource;


/**
 * Tests our mock gateway
 * @author Merlin
 *
 */
public class PlayerLoginRowDataGatewayMockTest extends PlayerLoginRowDataGatewayTest
{

	PlayerLoginRowDataGateway createRowDataGateway()
	{
		PlayerLoginRowDataGateway x = new PlayerLoginRowDataGatewayMock();
		return x;
	}

}
