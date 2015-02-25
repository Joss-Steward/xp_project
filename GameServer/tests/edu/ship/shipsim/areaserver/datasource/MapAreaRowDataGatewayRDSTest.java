package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;


/**
 * Tests for the RDS version of the gateway
 * @author Merlin
 *
 */
public class MapAreaRowDataGatewayRDSTest extends MapAreaRowDataGatewayTest
{

	@Override
	MapAreaRowDataGateway findGateway(String areaName) throws DatabaseException
	{
		return new MapAreaRowDataGatewayRDS(areaName);
	}

	

}
