package edu.ship.shipsim.areaserver.datasource;

/**
 * Tests the mock implementation
 * @author merlin
 *
 */
public class LevelTableDataGatewayMockTest extends
		LevelTableDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.LevelTableDataGatewayTest#getGateway()
	 */
	@Override
	public LevelTableDataGateway getGateway()
	{
		return  LevelTableDataGatewayMock.getSingleton();
	}

}
