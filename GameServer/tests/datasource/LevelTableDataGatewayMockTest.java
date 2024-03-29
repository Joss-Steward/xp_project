package datasource;

import datasource.LevelTableDataGateway;
import datasource.LevelTableDataGatewayMock;

/**
 * Tests the mock implementation
 * 
 * @author merlin
 *
 */
public class LevelTableDataGatewayMockTest extends LevelTableDataGatewayTest
{

	/**
	 * @see datasource.LevelTableDataGatewayTest#getGateway()
	 */
	@Override
	public LevelTableDataGateway getGateway()
	{
		return LevelTableDataGatewayMock.getSingleton();
	}

}
