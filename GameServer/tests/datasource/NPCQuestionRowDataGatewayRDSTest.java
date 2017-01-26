package datasource;

import datasource.DatabaseException;
import datasource.NPCQuestionRowDataGateway;
import datasource.NPCQuestionRowDataGatewayRDS;

/**
 * Tests for the RDS version of the gateway
 * 
 * @author Merlin
 *
 */
public class NPCQuestionRowDataGatewayRDSTest extends NPCQuestionRowDataGatewayTest
{

	/**
	 * 
	 * @see datasource.NPCQuestionRowDataGatewayTest#findGateway(int)
	 */
	@Override
	NPCQuestionRowDataGateway findGateway(int questionID) throws DatabaseException
	{
		return new NPCQuestionRowDataGatewayRDS(questionID);
	}

	/**
	 * @see datasource.NPCQuestionRowDataGatewayTest#findRandomGateway()
	 */
	@Override
	public NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
	{
		return NPCQuestionRowDataGatewayRDS.findRandomGateway();
	}

}
