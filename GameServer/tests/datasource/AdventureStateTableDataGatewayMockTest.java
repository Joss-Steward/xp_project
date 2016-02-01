package datasource;

import datasource.AdventureStateTableDataGateway;
import datasource.AdventureStateTableDataGatewayMock;

/**
 * Tests the mock implementation
 * @author merlin
 *
 */
public class AdventureStateTableDataGatewayMockTest extends
		AdventureStateTableDataGatewayTest
{

	/**
	 * @see datasource.AdventureStateTableDataGatewayTest#getGateway()
	 */
	@Override
	public AdventureStateTableDataGateway getGateway()
	{
		return AdventureStateTableDataGatewayMock.getSingleton();
	}

	
}
