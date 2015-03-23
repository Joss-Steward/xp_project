package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

import datasource.DatabaseException;


/**
 * Tests for the RDS version of the gateway
 * @author Merlin
 *
 */
public class NPCRowDataGatewayRDSTest extends NPCRowDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new NPCRowDataGatewayRDS(playerID);
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCRowDataGatewayTest#getAllForMap(java.lang.String)
	 */
	@Override
	public ArrayList<NPCRowDataGateway> getAllForMap(String mapName)
			throws DatabaseException
	{
		return NPCRowDataGatewayRDS.getNPCsForMap(mapName);
	}

}
