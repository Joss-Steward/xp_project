package edu.ship.shipsim.areaserver.datasource;

/**
 * Tests the mock implementation
 * 
 * @author merlin
 *
 */
public class PlayerTableDataGatewayMockTest extends PlayerTableDataGatewayTest
{

	
	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public PlayerTableDataGateway getGatewaySingleton()
	{
		return PlayerTableDataGatewayMock.getSingleton();
	}

}
