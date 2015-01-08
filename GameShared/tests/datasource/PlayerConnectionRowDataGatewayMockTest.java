package datasource;


/**
 * Test our mock data source
 * @author Merlin
 *
 */
public class PlayerConnectionRowDataGatewayMockTest extends PlayerConnectionRowDataGatewayTest
{

	/**
	 * @see datasource.PlayerConnectionRowDataGatewayTest#createRowDataGateway()
	 */
	@Override
	public PlayerConnectionRowDataGateway createRowDataGateway()
	{
		return new PlayerConnectionRowDataGatewayMock();
	}

}
