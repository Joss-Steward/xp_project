package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;


/**
 * Tests for the mock version of the gateway
 * @author Merlin
 *
 */
public class NPCQuestionRowDataGatewayMockTest extends NPCQuestionRowDataGatewayTest
{

	/**
	 * 
	 * @see edu.ship.shipsim.areaserver.datasource.NPCQuestionRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCQuestionRowDataGateway findGateway(int questionID) throws DatabaseException
	{
		return new NPCQuestionRowDataGatewayMock(questionID);
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCQuestionRowDataGatewayTest#findRandomGateway()
	 */
	@Override
	public NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
	{
		return NPCQuestionRowDataGatewayMock.findRandomGateway();
	}

	
}
