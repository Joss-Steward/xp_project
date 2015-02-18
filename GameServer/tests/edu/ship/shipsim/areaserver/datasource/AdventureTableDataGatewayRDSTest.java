package edu.ship.shipsim.areaserver.datasource;


public class AdventureTableDataGatewayRDSTest extends
		AdventureTableDataGatewayTest
{

	@Override
	public AdventureTableDataGateway getGateway()
	{
		return  AdventureTableDataGatewayRDS.getSingleton();
	}

}
