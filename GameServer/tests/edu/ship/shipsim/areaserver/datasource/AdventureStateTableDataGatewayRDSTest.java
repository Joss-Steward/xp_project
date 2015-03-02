package edu.ship.shipsim.areaserver.datasource;


/**
 * Tests the RDS Implementation
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayRDSTest extends
		AdventureStateTableDataGatewayTest
{

	
	/**
	 * @see edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGatewayTest#getGateway()
	 */
	@Override
	public AdventureStateTableDataGateway getGateway()
	{
		return  AdventureStateTableDataGatewayRDS.getSingleton();
	}

}
