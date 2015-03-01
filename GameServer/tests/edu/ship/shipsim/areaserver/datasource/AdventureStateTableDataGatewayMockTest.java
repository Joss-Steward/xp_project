package edu.ship.shipsim.areaserver.datasource;

/**
 * Tests the mock implementation
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayMockTest extends
		AdventureStateTableDataGatewayTest
{

	@Override
	public AdventureStateTableDataGateway getGateway()
	{
		return AdventureStateTableDataGatewayMock.getSingleton();
	}

	
}
