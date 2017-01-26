package datasource;

import datasource.LevelTableDataGateway;
import datasource.LevelTableDataGatewayRDS;

/**
 * Tests the RDS Implementation
 * 
 * @author merlin
 *
 */
public class LevelTableDataGatewayRDSTest extends LevelTableDataGatewayTest
{

	/**
	 * @see datasource.AdventureTableDataGatewayTest#getGateway()
	 */
	@Override
	public LevelTableDataGateway getGateway()
	{
		return LevelTableDataGatewayRDS.getSingleton();
	}

}
