package datasource;


/**
 * Test our RDS data source
 * @author Merlin
 *
 */
public class PlayerConnectionRowDataGatewayRDSTest extends PlayerConnectionRowDataGatewayTest
{

	/**
	 * @see datasource.PlayerConnectionRowDataGatewayTest#createRowDataGateway()
	 */
	@Override
	public PlayerConnectionRowDataGateway createRowDataGateway()
	{
		return new PlayerConnectionRowDataGatewayRDS();
	}

}
