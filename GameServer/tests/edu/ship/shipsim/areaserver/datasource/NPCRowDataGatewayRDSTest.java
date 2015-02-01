package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;


/**
 * Tests for the RDS version of the gateway
 * @author Merlin
 *
 */
public class NPCRowDataGatewayRDSTest extends NPCRowDataGatewayTest
{

	@Override
	NPCRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new NPCRowDataGatewayRDS(playerID);
	}

}
