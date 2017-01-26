package datasource;

import java.util.ArrayList;

import datasource.DatabaseException;
import datasource.NPCRowDataGateway;
import datasource.NPCRowDataGatewayMock;

/**
 * Tests for the mock version of the gateway
 * 
 * @author Merlin
 *
 */
public class NPCRowDataGatewayMockTest extends NPCRowDataGatewayTest
{

	/**
	 * @see datasource.NPCRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new NPCRowDataGatewayMock(playerID);
	}

	/**
	 * @see datasource.NPCRowDataGatewayTest#getAllForMap(java.lang.String)
	 */
	@Override
	public ArrayList<NPCRowDataGateway> getAllForMap(String mapName) throws DatabaseException
	{
		return NPCRowDataGatewayMock.getNPCsForMap(mapName);
	}
}
