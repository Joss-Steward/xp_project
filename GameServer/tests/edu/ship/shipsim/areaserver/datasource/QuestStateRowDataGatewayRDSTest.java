package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;


/**
 * Tests for the RDS version of the gateway
 * @author Merlin
 *
 */
public class QuestStateRowDataGatewayRDSTest extends QuestStateRowDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateRowDataGatewayTest#findGateway(int, int)
	 */
	@Override
	QuestStateRowDataGateway findGateway(int playerID, int questID)
			throws DatabaseException
	{
		return new QuestStateRowDataGatewayRDS(playerID, questID);
	}

}
