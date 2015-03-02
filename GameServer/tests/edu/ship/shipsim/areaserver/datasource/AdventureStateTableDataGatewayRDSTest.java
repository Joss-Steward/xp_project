package edu.ship.shipsim.areaserver.datasource;


/**
 * Tests the RDS Implementation
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayRDSTest extends
		AdventureStateTableDataGatewayTest
{

	
	@Override
	public AdventureStateTableDataGateway getGateway()
	{
		return  AdventureStateTableDataGatewayRDS.getSingleton();
	}

}
