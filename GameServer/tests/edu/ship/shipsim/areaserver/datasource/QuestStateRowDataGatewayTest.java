package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.QuestStateList;

/**
 * Tests required of all player gateways
 * 
 * @author Merlin
 *
 */
public abstract class QuestStateRowDataGatewayTest extends DatabaseTest
{

	private QuestStateRowDataGateway gateway;

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
		QuestStatesForTest quest = QuestStatesForTest.PLAYER1_QUEST1;
		gateway = findGateway(quest.getQuestID(),quest.getPlayerID());
		assertEquals(quest.getQuestID(), gateway.getQuestID());
		assertEquals(quest.getPlayerID(), gateway.getPlayerID());
		assertEquals(quest.getQuestState(), gateway.getQuestState());
	}

	/**
	 * if a quest state is not in the data source, then its state should be hidden
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test
	public void findNotExisting() throws DatabaseException
	{
		gateway = findGateway(3,1);
		assertEquals(QuestStateList.HIDDEN, gateway.getQuestState());
	}


	/**
	 * Get a gateway for a given playerID and questID
	 * @param playerID the player we are interested in
	 * @param questID the quest we are interested in
	 * @return the gateway
	 * @throws DatabaseException if we fail to talk to the data source
	 */
	abstract QuestStateRowDataGateway findGateway(int playerID, int questID) throws DatabaseException;
}
