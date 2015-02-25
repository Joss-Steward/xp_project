package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;

/**
 * Tests the mock version of the gateway
 * @author Merlin
 *
 */
public class MapAreaRowDataGatewayMockTest extends MapAreaRowDataGatewayTest
{

	@Override
	MapAreaRowDataGateway findGateway(String areaName) throws DatabaseException
	{
		return new MapAreaRowDataGatewayMock(areaName);
	}

}
