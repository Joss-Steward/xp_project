package datasource;


/**
 * Tests the RDS gateway
 * @author Merlin
 *
 */
public class PlayerLoginRowDataGatewayRDSTest extends PlayerLoginRowDataGatewayTest
{

	PlayerLoginRowDataGateway createRowDataGateway()
	{
		PlayerLoginRowDataGateway x = new PlayerLoginRowDataGatewayRDS();
		return x;
	}
}
