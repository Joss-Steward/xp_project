package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;

import data.Position;
import datasource.DatabaseException;

/**
 * Tests for the RDS version of the gateway
 * 
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

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestRowDataGatewayTest#findQuestsForMapLocation(java.lang.String,
	 *      data.Position)
	 */
	@Override
	ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position)
			throws DatabaseException
	{
		return QuestRowDataGatewayRDS.findQuestsForMapLocation(mapName, position);
	}
}
