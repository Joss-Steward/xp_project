package edu.ship.shipsim.areaserver.datasource;

import data.Position;
import datasource.DatabaseException;


/**
 * Tests for the RDS version of the gateway
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayRDSTest extends PlayerRowDataGatewayTest
{

	@Override
	PlayerRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new PlayerRowDataGatewayRDS(playerID);
	}

	@Override
	PlayerRowDataGateway createGateway(String mapName, Position position,
			String appearanceType) throws DatabaseException
	{
		return new PlayerRowDataGatewayRDS(mapName, position, appearanceType);
	}

	

}
