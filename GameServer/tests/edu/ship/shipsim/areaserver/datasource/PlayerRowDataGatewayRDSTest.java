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

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayTest#findGateway(int)
	 */
	@Override
	PlayerRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new PlayerRowDataGatewayRDS(playerID);
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGatewayTest#createGateway(java.lang.String, data.Position, java.lang.String, int, int)
	 */
	@Override
	PlayerRowDataGateway createGateway(String mapName, Position position,
			String appearanceType, int quizScore, int experiencePoints) throws DatabaseException
	{
		return new PlayerRowDataGatewayRDS(mapName, position, appearanceType, quizScore, experiencePoints);
	}

	

}
