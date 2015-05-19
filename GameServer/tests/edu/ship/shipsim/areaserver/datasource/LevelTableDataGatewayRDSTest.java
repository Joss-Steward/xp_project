package edu.ship.shipsim.areaserver.datasource;


/**
 * Tests the RDS Implementation
 * @author merlin
 *
 */
public class LevelTableDataGatewayRDSTest extends
		LevelTableDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayTest#getGateway()
	 */
	@Override
	public LevelTableDataGateway getGateway()
	{
		return  LevelTableDataGatewayRDS.getSingleton();
	}

}
