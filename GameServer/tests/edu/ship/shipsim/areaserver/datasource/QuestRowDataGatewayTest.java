package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;

/**
 * Tests required of all player gateways
 * 
 * @author Merlin
 *
 */
public abstract class QuestRowDataGatewayTest extends DatabaseTest
{

	private QuestRowDataGateway gateway;

	/**
	 * Make sure any static information is cleaned up between tests
	 */
	@After
	public void cleanup()
	{
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
		QuestsForTest quest = QuestsForTest.ONE_BIG_QUEST;
		gateway = findGateway(QuestsForTest.ONE_BIG_QUEST.ordinal() + 1);
		assertEquals(quest.getQuestID(), gateway.getQuestID());
		assertEquals(quest.getQuestDescription(), gateway.getQuestDescription());
	}

	/**
	 * make sure we get the right exception if we try to find a quest who
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


	abstract QuestRowDataGateway findGateway(int questID) throws DatabaseException;
}
