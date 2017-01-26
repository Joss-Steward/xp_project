package datasource;

import datasource.PlayerTableDataGateway;
import datasource.PlayerTableDataGatewayRDS;

/**
 * Tests the mock implementation
 * 
 * @author merlin
 *
 */
public class PlayerTableDataGatewayRDSTest extends PlayerTableDataGatewayTest
{

	/**
	 * @see datasource.PlayerTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public PlayerTableDataGateway getGatewaySingleton()
	{
		return PlayerTableDataGatewayRDS.getSingleton();
	}

}
