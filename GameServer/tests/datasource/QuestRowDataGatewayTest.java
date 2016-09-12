package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testData.QuestsForTest;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.QuestRowDataGateway;
import datatypes.Position;

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
	 * Makes sure to reset the gateway
	 * @throws DatabaseException shouldn't
	 */
	@Before
	public void setup() throws DatabaseException
	{
		gateway = this.findGateway(1);
		gateway.resetData();
	}
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
		QuestsForTest quest = QuestsForTest.ONE_BIG_QUEST;
		gateway = findGateway(QuestsForTest.ONE_BIG_QUEST.getQuestID());
		assertEquals(quest.getQuestID(), gateway.getQuestID());
		assertEquals(quest.getQuestDescription(), gateway.getQuestDescription());
		assertEquals(quest.getMapName(), gateway.getTriggerMapName());
		assertEquals(quest.getPosition(), gateway.getTriggerPosition());
		assertEquals(quest.getAdventuresForFulfillment(), gateway.getAdventuresForFulfillment());
		assertEquals(quest.getExperienceGained(), gateway.getExperiencePointsGained());
		assertEquals(quest.getCompletionActionType(), gateway.getCompletionActionType());
		assertEquals(quest.getCompletionActionParameter(), gateway.getCompletionActionParameter());
		assertEquals(quest.getQuestTitle(), gateway.getQuestTitle());
		assertEquals(quest.getStartDate(), gateway.getStartDate());
		assertEquals(quest.getEndDate(), gateway.getEndDate());
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
	 * There are three quests in QuestsForTest that are on the same location.  Make sure we get them all
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canFindQuestsForMapLocation() throws DatabaseException
	{
		QuestsForTest q = QuestsForTest.THE_OTHER_QUEST;
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
