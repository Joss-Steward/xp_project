package datasource;

import java.util.ArrayList;

import datasource.DatabaseException;
import datasource.NPCRowDataGateway;
import datasource.NPCRowDataGatewayRDS;


/**
 * Tests for the RDS version of the gateway
 * @author Merlin
 *
 */
public class NPCRowDataGatewayRDSTest extends NPCRowDataGatewayTest
{

	/**
	 * @see datasource.NPCRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCRowDataGateway findGateway(int playerID) throws DatabaseException
	{
		return new NPCRowDataGatewayRDS(playerID);
	}

	/**
	 * @see datasource.NPCRowDataGatewayTest#getAllForMap(java.lang.String)
	 */
	@Override
	public ArrayList<NPCRowDataGateway> getAllForMap(String mapName)
			throws DatabaseException
	{
		return NPCRowDataGatewayRDS.getNPCsForMap(mapName);
	}

}
