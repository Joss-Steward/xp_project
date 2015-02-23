package edu.ship.shipsim.areaserver.datasource;

/**
 * Tests the mock implementation
 * @author merlin
 *
 */
public class AdventureTableDataGatewayMockTest extends
		AdventureTableDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayTest#getGateway()
	 */
	@Override
	public AdventureTableDataGateway getGateway()
	{
		return  AdventureTableDataGatewayMock.getSingleton();
	}

}
