package edu.ship.shipsim.areaserver.datasource;

import data.Position;
import datasource.DatabaseException;


/**
 * Tests for the mock version of the gateway
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayMockTest extends PlayerRowDataGatewayTest
{

	@Override
	PlayerRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new PlayerRowDataGatewayMock(playerID);
	}

	@Override
	PlayerRowDataGateway createGateway(String mapName, Position position,
			String appearanceType) throws DatabaseException
	{
		return new PlayerRowDataGatewayMock(mapName, position, appearanceType, 0);
	}

	

}
