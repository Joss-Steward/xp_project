package datasource;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;

import org.junit.After;
import org.junit.Test;

import testData.NPCQuestionsForTest;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.NPCQuestionRowDataGateway;

/**
 * Tests required of all player gateways
 * 
 * @author Merlin
 *
 */
public abstract class NPCQuestionRowDataGatewayTest extends DatabaseTest
{

	private NPCQuestionRowDataGateway gateway;

	/**
	 * Make sure any static information is cleaned up between tests
	 * @throws SQLException shouldn't
	 * @throws DatabaseException shouldn't
	 */
	@After
	public void cleanup() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * Make sure we can retrieve a specific question
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void finder() throws DatabaseException
	{
		NPCQuestionsForTest question = NPCQuestionsForTest.ONE;
		gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());
		assertEquals(question.getQ(), gateway.getQuestionStatement());
		assertEquals(question.getA(), gateway.getAnswer());
		
		assertEquals(question.getStartDate(), gateway.getStartDate());
		assertEquals(question.getEndDate(), gateway.getEndDate());
	}

	/**
	 * make sure we get the right exception if we try to find someone who
	 * doesn't exist
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void findNotExisting() throws DatabaseException
	{
		gateway = findGateway(11111);
	}

	/**
	 * @return a random gateway
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public abstract NPCQuestionRowDataGateway findRandomGateway()
			throws DatabaseException;

	/**
	 * Make sure we get a gateway when we ask for a random one
	 * It runs the test multiple times to help ensure the random works
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void randomQuestion() throws DatabaseException
	{
	    for(int i = 0; i < 10; i++)
	    {
	        gateway = findRandomGateway();
	        assertNotNull(gateway);

	        Date now = new Date();

	        assertTrue(now.after(gateway.getStartDate()));
	        assertFalse(gateway.getQuestionStatement().equals(NPCQuestionsForTest.TWO.getQ()));
	    }
	}
	
	

	/**
	 * get a gateway for a given question
	 * @param questionID the unique ID of the question we are interested in
	 * @return the gateway
	 * @throws DatabaseException if we couldn't find the ID in the data source
	 */
	abstract NPCQuestionRowDataGateway findGateway(int questionID) throws DatabaseException;
}
