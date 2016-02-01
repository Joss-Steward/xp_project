package datasource;

import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayMock;

/**
 * Tests the mock implementation
 * 
 * @author merlin
 *
 */
public class QuestStateTableDataGatewayMockTest extends QuestStateTableDataGatewayTest
{

	/**
	 * @see datasource.QuestStateTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public QuestStateTableDataGateway getGatewaySingleton()
	{
		return QuestStateTableDataGatewayMock.getSingleton();
	}

}
