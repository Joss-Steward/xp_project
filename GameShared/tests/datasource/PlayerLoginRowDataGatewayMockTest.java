package datasource;


public class PlayerLoginRowDataGatewayMockTest extends PlayerLoginRowDataGatewayTest
{

	PlayerLoginRowDataGateway createRowDataGateway()
	{
		PlayerLoginRowDataGateway x = new PlayerLoginRowDataGatewayMock();
		return x;
	}

}
