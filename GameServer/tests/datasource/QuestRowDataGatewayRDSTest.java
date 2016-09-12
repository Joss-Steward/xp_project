package datasource;

import java.util.ArrayList;

import datasource.DatabaseException;
import datasource.QuestRowDataGateway;
import datasource.QuestRowDataGatewayRDS;
import datatypes.Position;

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
	 * @see datasource.QuestRowDataGatewayTest#findGateway(int)
	 */
	@Override
	QuestRowDataGateway findGateway(int questID) throws DatabaseException
	{
		return new QuestRowDataGatewayRDS(questID);
	}

	/**
	 * @see datasource.QuestRowDataGatewayTest#findQuestsForMapLocation(java.lang.String,
	 *      datatypes.Position)
	 */
	@Override
	ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position)
			throws DatabaseException
	{
		return QuestRowDataGatewayRDS.findQuestsForMapLocation(mapName, position);
	}
}
