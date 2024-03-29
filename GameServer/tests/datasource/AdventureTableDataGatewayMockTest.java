package datasource;

import datasource.AdventureTableDataGateway;
import datasource.AdventureTableDataGatewayMock;

/**
 * Tests the mock implementation
 * 
 * @author merlin
 *
 */
public class AdventureTableDataGatewayMockTest extends AdventureTableDataGatewayTest
{

	/**
	 * @see datasource.AdventureTableDataGatewayTest#getGateway()
	 */
	@Override
	public AdventureTableDataGateway getGateway()
	{
		return AdventureTableDataGatewayMock.getSingleton();
	}

}
