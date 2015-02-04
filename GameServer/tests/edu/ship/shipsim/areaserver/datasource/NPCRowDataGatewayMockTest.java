package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

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

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCRowDataGatewayTest#getAllForMap(java.lang.String)
	 */
	@Override
	public ArrayList<NPCRowDataGateway> getAllForMap(String mapName) throws DatabaseException
	{
		return NPCRowDataGatewayMock.getNPCsForMap(mapName);
	}
}
