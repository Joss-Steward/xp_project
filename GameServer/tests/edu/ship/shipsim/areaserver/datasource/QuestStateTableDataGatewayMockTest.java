package edu.ship.shipsim.areaserver.datasource;

/**
 * Tests the mock implementation
 * 
 * @author merlin
 *
 */
public class QuestStateTableDataGatewayMockTest extends QuestStateTableDataGatewayTest
{

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public QuestStateTableDataGateway getGatewaySingleton()
	{
		return QuestStateTableDataGatewayMock.getSingleton();
	}

}
