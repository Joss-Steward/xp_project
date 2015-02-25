package edu.ship.shipsim.areaserver.datasource;

import datasource.DatabaseException;


/**
 * Tests for the RDS version of the gateway
 * @author Merlin
 *
 */
public class QuestRowDataGatewayRDSTest extends QuestRowDataGatewayTest
{

	/**
	 * 
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayTest#findGateway(int)
	 */
	@Override
	QuestRowDataGateway findGateway(int questID) throws DatabaseException
	{
		return new QuestRowDataGatewayRDS(questID);
	}
}
