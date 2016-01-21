package datasource;

import datasource.QuestStateTableDataGateway;
import datasource.QuestStateTableDataGatewayRDS;

/**
 * Tests the mock implementation
 * 
 * @author merlin
 *
 */
public class QuestStateTableDataGatewayRDSTest extends QuestStateTableDataGatewayTest
{

	/**
	 * @see datasource.QuestStateTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public QuestStateTableDataGateway getGatewaySingleton()
	{
		return QuestStateTableDataGatewayRDS.getSingleton();
	}

}
