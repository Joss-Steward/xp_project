package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import data.Position;
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
		assertEquals(quest.getMapName(), gateway.getTriggerMapName());
		assertEquals(quest.getPosition(), gateway.getTriggerPosition());
		assertEquals(quest.getAdventuersForFulfillment(), gateway.getAdventuresForFulfillment());
		assertEquals(quest.getExperienceGained(), gateway.getExperiencePointsGained());
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

	/**
	 * There are two quests in QuestsForTest that are on the same location.  Make sure we get them both
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canFindQuestsForMapLocation() throws DatabaseException
	{
		QuestsForTest q = QuestsForTest.ONE_BIG_QUEST;
		ArrayList<Integer> questIDs = findQuestsForMapLocation(q.getMapName(),q.getPosition());
		assertEquals(2, questIDs.size());
		assertTrue(questIDs.contains(q.getQuestID()));
		assertTrue(questIDs.contains(QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID()));
	}

	/**
	 * Use the appropriate gateway to find the quests on a given map location
	 * @param mapName the name of the map
	 * @param position the position on the map
	 * @return a list of quest IDs for quests that are triggered at the given location
	 * @throws DatabaseException shouldn't
	 */
	abstract ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position) throws DatabaseException;

	/**
	 * Get a gateway for these tests to use
	 * @param questID the ID of the quest the gateway should manage
	 * @return the gateway
	 * @throws DatabaseException if the gateway can't be created
	 */
	abstract QuestRowDataGateway findGateway(int questID) throws DatabaseException;
}
