package datasource;


/**
 * Test the AWS RDS data source
 * @author Merlin
 *
 */
public class ServerDataBehaviorRDSTest extends ServerDataBehaviorTest
{

	ServerDataBehavior createBehavior()
	{
		return new ServerDataBehaviorRDS();
	}
}
