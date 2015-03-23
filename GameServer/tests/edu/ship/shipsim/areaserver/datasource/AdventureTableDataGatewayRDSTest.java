package edu.ship.shipsim.areaserver.datasource;


/**
 * Tests the RDS Implementation
 * @author merlin
 *
 */
public class AdventureTableDataGatewayRDSTest extends
		AdventureTableDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureTableDataGatewayTest#getGateway()
	 */
	@Override
	public AdventureTableDataGateway getGateway()
	{
		return  AdventureTableDataGatewayRDS.getSingleton();
	}

}
