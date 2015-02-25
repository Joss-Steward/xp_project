package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;


/**
 * Tests for the mock version of the gateway
 * @author Merlin
 *
 */
public class QuestRowDataGatewayMockTest extends QuestRowDataGatewayTest
{

	/**
	 * 
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayTest#findGateway(int)
	 */
	@Override
	QuestRowDataGateway findGateway(int questionID) throws DatabaseException
	{
		return new QuestRowDataGatewayMock(questionID);
	}

	
}
