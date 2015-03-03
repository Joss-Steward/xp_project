package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

import data.Position;
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

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayTest#findQuestsForMapLocation(java.lang.String, data.Position)
	 */
	@Override
	ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position) throws DatabaseException
	{
		return QuestRowDataGatewayMock.getQuestsForMapLocation(mapName, position);
	}

	
}
