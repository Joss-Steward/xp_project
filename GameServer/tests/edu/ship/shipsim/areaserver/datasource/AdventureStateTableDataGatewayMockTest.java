package edu.ship.shipsim.areaserver.datasource;

/**
 * Tests the mock implementation
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayMockTest extends
		AdventureStateTableDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGatewayTest#getGateway()
	 */
	@Override
	public AdventureStateTableDataGateway getGateway()
	{
		return AdventureStateTableDataGatewayMock.getSingleton();
	}

	
}
