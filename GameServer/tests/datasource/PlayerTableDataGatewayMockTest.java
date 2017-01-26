package datasource;

import datasource.PlayerTableDataGateway;
import datasource.PlayerTableDataGatewayMock;

/**
 * Tests the mock implementation
 * 
 * @author merlin
 *
 */
public class PlayerTableDataGatewayMockTest extends PlayerTableDataGatewayTest
{

	/**
	 * @see datasource.PlayerTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public PlayerTableDataGateway getGatewaySingleton()
	{
		return PlayerTableDataGatewayMock.getSingleton();
	}

}
