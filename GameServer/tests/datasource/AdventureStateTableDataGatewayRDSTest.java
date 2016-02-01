package datasource;

import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayRDS;


/**
 * Tests the RDS Implementation
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayRDSTest extends
		AdventureStateTableDataGatewayTest
{

	
	/**
	 * @see datasource.AdventureStateTableDataGatewayTest#getGateway()
	 */
	@Override
	public AdventureStateTableDataGateway getGateway()
	{
		return  AdventureStateTableDataGatewayRDS.getSingleton();
	}

}
