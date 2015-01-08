package datasource;


/**
 * Test the AWS RDS data source
 * @author Merlin
 *
 */
public class ServerRowDataGatewayRDSTest extends ServerRowDataGatewayTest
{

	ServerRowDataGateway createGateway()
	{
		return new ServerRowDataGatewayRDS();
	}
}
