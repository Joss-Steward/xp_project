package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;


/**
 * Tests for the RDS version of the gateway
 * @author Merlin
 *
 */
public class MapAreaRowDataGatewayRDSTest extends MapAreaRowDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGatewayTest#findGateway(java.lang.String)
	 */
	@Override
	MapAreaRowDataGateway findGateway(String areaName) throws DatabaseException
	{
		return new MapAreaRowDataGatewayRDS(areaName);
	}

	

}
