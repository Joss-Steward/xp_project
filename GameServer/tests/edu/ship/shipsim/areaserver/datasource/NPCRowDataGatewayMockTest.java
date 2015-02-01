package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;


/**
 * Tests for the mock version of the gateway
 * @author Merlin
 *
 */
public class NPCRowDataGatewayMockTest extends NPCRowDataGatewayTest
{

	@Override
	NPCRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new NPCRowDataGatewayMock(playerID);
	}
}
