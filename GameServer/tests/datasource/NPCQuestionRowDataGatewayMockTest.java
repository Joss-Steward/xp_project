package datasource;

import datasource.DatabaseException;
import datasource.NPCQuestionRowDataGateway;
import datasource.NPCQuestionRowDataGatewayMock;


/**
 * Tests for the mock version of the gateway
 * @author Merlin
 *
 */
public class NPCQuestionRowDataGatewayMockTest extends NPCQuestionRowDataGatewayTest
{

	/**
	 * 
	 * @see datasource.NPCQuestionRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCQuestionRowDataGateway findGateway(int questionID) throws DatabaseException
	{
		return new NPCQuestionRowDataGatewayMock(questionID);
	}

	/**
	 * @see datasource.NPCQuestionRowDataGatewayTest#findRandomGateway()
	 */
	@Override
	public NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
	{
		return NPCQuestionRowDataGatewayMock.findRandomGateway();
	}

	
}
