package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;


/**
 * Tests for the mock version of the gateway
 * @author Merlin
 *
 */
public class QuestStateRowDataGatewayMockTest extends QuestStateRowDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateRowDataGatewayTest#findGateway(int, int)
	 */
	@Override
	QuestStateRowDataGateway findGateway(int playerID, int questID)
			throws DatabaseException
	{
		return new QuestStateRowDataGatewayMock(playerID, questID);
	}


	
}
