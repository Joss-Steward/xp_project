package datasource;


public class PlayerLoginRowDataGatewayRDSTest extends PlayerLoginRowDataGatewayTest
{

	PlayerLoginRowDataGateway createRowDataGateway()
	{
		PlayerLoginRowDataGateway x = new PlayerLoginRowDataGatewayRDS();
		return x;
	}
}
