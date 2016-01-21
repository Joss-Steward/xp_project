package datasource;

import datasource.AdventureTableDataGateway;
import datasource.AdventureTableDataGatewayRDS;


/**
 * Tests the RDS Implementation
 * @author merlin
 *
 */
public class AdventureTableDataGatewayRDSTest extends
		AdventureTableDataGatewayTest
{

	/**
	 * @see datasource.AdventureTableDataGatewayTest#getGateway()
	 */
	@Override
	public AdventureTableDataGateway getGateway()
	{
		return  AdventureTableDataGatewayRDS.getSingleton();
	}

}
