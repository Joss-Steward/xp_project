package datasource;

import java.util.ArrayList;

import data.Position;
import datasource.DatabaseException;
import datasource.QuestRowDataGateway;
import datasource.QuestRowDataGatewayMock;


/**
 * Tests for the mock version of the gateway
 * @author Merlin
 *
 */
public class QuestRowDataGatewayMockTest extends QuestRowDataGatewayTest
{

	/**
	 * 
	 * @see datasource.QuestRowDataGatewayTest#findGateway(int)
	 */
	@Override
	QuestRowDataGateway findGateway(int questionID) throws DatabaseException
	{
		return new QuestRowDataGatewayMock(questionID);
	}

	/**
	 * @see datasource.QuestRowDataGatewayTest#findQuestsForMapLocation(java.lang.String, data.Position)
	 */
	@Override
	ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position) throws DatabaseException
	{
		return QuestRowDataGatewayMock.findQuestsForMapLocation(mapName, position);
	}

	
}
