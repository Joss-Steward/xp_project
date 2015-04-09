package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import data.Position;
import datasource.DatabaseException;
import datasource.QuestStateEnum;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;
import edu.ship.shipsim.areaserver.datasource.QuestStateTableDataGatewayMock;
import edu.ship.shipsim.areaserver.datasource.QuestStatesForTest;
import edu.ship.shipsim.areaserver.datasource.QuestsForTest;

/**
 * Test for the quest manager getting quests and adventures from database
 * 
 * @author lavonnediller
 *
 */
public class QuestManagerTest
{

	/**
	 * Set up Options Manager, run in TestMode
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton(true);
		PlayerManager.resetSingleton();
		playerManager = PlayerManager.getSingleton();
		QuestStateTableDataGatewayMock.getSingleton().resetData();
		QuestManager.resetSingleton();
		QuestManager.getSingleton();
	}

	/**
	 * Test initializing a quest manager
	 */
	@Test
	public void testInitialization()
	{
		QuestManager.resetSingleton();
		QuestManager questManager = QuestManager.getSingleton();
		assertNotNull(questManager);
	}

	/**
	 * Make sure QuestManager is a resetable singleton
	 */
	@Test
	public void testIsSingleton()
	{
		QuestManager qm1 = QuestManager.getSingleton();
		QuestManager qm2 = QuestManager.getSingleton();

		assertSame(qm1, qm2);

		QuestManager.resetSingleton();
		assertNotSame(qm1, QuestManager.getSingleton());
	}

	/**
	 * Test getting a quest from the database and storing it into our Quest
	 * Manager, using a Quest Row Data Gateway Mock
	 * 
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingOneQuest() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		QuestsForTest expected = QuestsForTest.ONE_BIG_QUEST;
		Quest actual = qm.getQuest(1);
		assertEquals(expected.getQuestID(), actual.getQuestID());
		assertEquals(expected.getQuestDescription(), actual.getDescription());
		assertEquals(expected.getMapName(), actual.getMapName());
		assertEquals(expected.getAdventuresForFulfillment(), actual.getAdventuresForFulfillment());
		assertEquals(expected.getExperienceGained(), actual.getExperiencePointsGained());
	}

	/**
	 * Test getting two quests from the database
	 * 
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingTwoQuests() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();

		assertEquals(1, qm.getQuest(1).getQuestID());
		assertEquals("A Big Quest", qm.getQuest(1).getDescription());

		assertEquals(2, qm.getQuest(2).getQuestID());
		assertEquals("The Other Quest", qm.getQuest(2).getDescription());
	}

	/**
	 * Test that we can get a specific quests adventures
	 * 
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingAQuestsAdventures() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		Quest quest = qm.getQuest(1);

		int i = 1;
		for (Adventure a : quest.getAdventures())
		{
			if (i == 1)
			{
				assertEquals(AdventuresForTest.ONE.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.ONE.getAdventureDescription(),
						a.getDescription());
			}
			if (i == 2)
			{
				assertEquals(AdventuresForTest.TWO.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.TWO.getAdventureDescription(),
						a.getDescription());
			}
			i++;
		}
	}

	/**
	 * Test getting two quests and their two adventures from the db
	 * 
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingTwoQuestsAndTheirAdventures() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		Quest quest1 = qm.getQuest(1);
		Quest quest2 = qm.getQuest(2);

		int i = 1;
		for (Adventure a : quest1.getAdventures())
		{
			if (i == 1)
			{
				assertEquals(AdventuresForTest.ONE.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.ONE.getAdventureDescription(),
						a.getDescription());
			}
			if (i == 2)
			{
				assertEquals(AdventuresForTest.TWO.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.TWO.getAdventureDescription(),
						a.getDescription());
			}
			i++;
		}

		i = 1;
		for (Adventure a : quest2.getAdventures())
		{
			if (i == 1)
			{
				assertEquals(AdventuresForTest.THREE.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.THREE.getAdventureDescription(),
						a.getDescription());
			}
			if (i == 2)
			{
				assertEquals(AdventuresForTest.FOUR.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.FOUR.getAdventureDescription(),
						a.getDescription());
			}
			i++;
		}
	}

	/**
	 * A Database Exception should be thrown if a quest id does not exist
	 * 
	 * @throws DatabaseException
	 *             throw an exception if the quest id isn't found
	 */
	@SuppressWarnings("unused")
	@Test(expected = DatabaseException.class)
	public void testQuestDoesNotExits() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		Quest quest1 = qm.getQuest(5);
	}

	/**
	 * Test getting quests by a position and map name
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testGetQuestsPosition() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();

		Position pos = QuestsForTest.ONE_BIG_QUEST.getPosition();

		int index = 1;
		for (Integer i : qm.getQuestsByPosition(pos,
				QuestsForTest.ONE_BIG_QUEST.getMapName()))
		{
			if (index == 1)
			{
				assertEquals((int) i, QuestsForTest.ONE_BIG_QUEST.getQuestID());
			}
			if (index == 3)
			{
				assertEquals((int) i, QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID());
			}
			index++;
		}
	}

	private PlayerManager playerManager;

	/**
	 * If there are no quests, we should get an empty arraylist - not null
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void notNullIfThereAreNoQuestsAtAPosition() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();

		Position pos = new Position(4, 4);
		ArrayList<Integer> actual = qm.getQuestsByPosition(pos,
				QuestsForTest.ONE_BIG_QUEST.getMapName());
		assertEquals(0, actual.size());
	}

	/**
	 * Test simple functionality of setting quests to a player.
	 */
	@Test
	public void testAddQuests()
	{
		Player p = playerManager.addPlayer(1);
		QuestState quest = new QuestState(15, QuestStateEnum.AVAILABLE);
		QuestManager.getSingleton().addQuestState(p.getPlayerID(), quest);

		assertEquals(QuestStateEnum.AVAILABLE, QuestManager.getSingleton()
				.getQuestStateByID(1, 15).getStateValue());

	}

	/**
	 * Make sure quest is triggered if it walks onto a location that has a quest
	 * 
	 * @throws DatabaseException
	 *             QuestManager works with DB
	 */
	@Test
	public void testPlayerTriggerOnMovement() throws DatabaseException
	{
		Position pos1 = new Position(1, 1);
		Position pos2 = new Position(4, 3);
		Player p = playerManager.addPlayer(1);
		p.setMapName("current.tmx");
		p.setPlayerPosition(pos1);
		assertEquals(
				QuestStatesForTest.PLAYER1_QUEST1.getState(),
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),
								QuestStatesForTest.PLAYER1_QUEST1.getQuestID())
						.getStateValue());
		p.setPlayerPosition(pos2);
		assertEquals(
				QuestStateEnum.TRIGGERED,
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),
								QuestStatesForTest.PLAYER1_QUEST1.getQuestID())
						.getStateValue());
		p.setPlayerPosition(new Position(0, 8));
	}

	/**
	 * Make sure quest is triggered within player
	 */
	@Test
	public void testPlayerTriggersQuest()
	{
		Player p = playerManager.addPlayer(1);
		assertEquals(
				QuestStatesForTest.PLAYER1_QUEST1.getState(),
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),
								QuestStatesForTest.PLAYER1_QUEST1.getQuestID())
						.getStateValue());

		QuestManager.getSingleton().triggerQuest(1,
				QuestStatesForTest.PLAYER1_QUEST1.getQuestID());
		assertEquals(
				QuestStateEnum.TRIGGERED,
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),
								QuestStatesForTest.PLAYER1_QUEST1.getQuestID())
						.getStateValue());
	}

	/**
	 * When a player moves to the right place, we should trigger the quest
	 */
	@Test
	public void triggersOnPlayerMovement()
	{
		Player p = playerManager.addPlayer(1);
		p.setPlayerPosition(QuestsForTest.ONE_BIG_QUEST.getPosition());
		assertEquals(
				QuestStateEnum.TRIGGERED,
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),
								QuestStatesForTest.PLAYER1_QUEST1.getQuestID())
						.getStateValue());
	}

	/**
	 * We should be able to clear out all of the quest states for a given player
	 */
	@Test
	public void canRemoveAPlayersQuestStates()
	{
		QuestManager.getSingleton().addQuestState(4, new QuestState(1,QuestStateEnum.AVAILABLE));
		QuestManager.getSingleton().removeQuestStatesForPlayer(4);
		assertNull(QuestManager.getSingleton().getQuestList(4));
	}
}
