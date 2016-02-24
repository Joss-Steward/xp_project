package model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import model.AdventureState;
import model.IllegalAdventureChangeException;
import model.IllegalQuestChangeException;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.QualifiedObservableConnector;
import model.Quest;
import model.QuestManager;
import model.QuestState;
import model.reports.PlayerLeaveReport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testData.AdventuresForTest;
import testData.PlayersForTest;
import testData.QuestStatesForTest;
import testData.QuestsForTest;
import data.AdventureRecord;
import data.AdventureStateEnum;
import data.GameLocation;
import data.Position;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.QuestStateEnum;
import datasource.QuestStateTableDataGatewayMock;

/**
 * Test for the quest manager getting quests and adventures from database
 * 
 * @author lavonnediller
 *
 */
public class QuestManagerTest extends DatabaseTest
{

	/**
	 * Set up Options Manager, run in TestMode
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setTestMode(true);
		QualifiedObservableConnector.resetSingleton();
		PlayerManager.resetSingleton();
		playerManager = PlayerManager.getSingleton();
		QuestStateTableDataGatewayMock.getSingleton().resetData();
		QuestManager.resetSingleton();
		QuestManager.getSingleton();
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
		QuestStateTableDataGatewayMock.getSingleton().resetData();
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
	 * We should be able to retrieve data about a specific adventure
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testGettingOneAdventure() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		AdventuresForTest expected = AdventuresForTest.QUEST3_ADVENTURE1;
		AdventureRecord actual = qm.getAdventure(expected.getQuestID(),
				expected.getAdventureID());
		assertEquals(AdventuresForTest.QUEST3_ADVENTURE1.getAdventureDescription(),
				actual.getAdventureDescription());
		assertEquals(AdventuresForTest.QUEST3_ADVENTURE1.getAdventureID(),
				actual.getAdventureID());
		assertEquals(AdventuresForTest.QUEST3_ADVENTURE1.getExperiencePointsGained(),
				actual.getExperiencePointsGained());
		assertEquals(AdventuresForTest.QUEST3_ADVENTURE1.getQuestID(),
				actual.getQuestID());
	}
	
	/**
	 * If we ask for an adventure that doesn't exist, we should get null
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testGettingMissingAdventure() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		AdventureRecord actual = qm.getAdventure(42,16);
		assertNull(actual);
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
		assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), qm.getQuest(1).getDescription());

		assertEquals(2, qm.getQuest(2).getQuestID());
		assertEquals(QuestsForTest.THE_OTHER_QUEST.getQuestDescription(), qm.getQuest(2).getDescription());
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
		for (AdventureRecord a : quest.getAdventures())
		{
			if (i == 1)
			{
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(),
						a.getAdventureID());
				assertEquals(
						AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),
						a.getAdventureDescription());
			}
			if (i == 2)
			{
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureID(),
						a.getAdventureID());
				assertEquals(
						AdventuresForTest.QUEST1_ADVENTURE2.getAdventureDescription(),
						a.getAdventureDescription());
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
		for (AdventureRecord a : quest1.getAdventures())
		{
			if (i == 1)
			{
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(),
						a.getAdventureID());
				assertEquals(
						AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),
						a.getAdventureDescription());
			}
			if (i == 2)
			{
				assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureID(),
						a.getAdventureID());
				assertEquals(
						AdventuresForTest.QUEST1_ADVENTURE2.getAdventureDescription(),
						a.getAdventureDescription());
			}
			i++;
		}

		i = 1;
		for (AdventureRecord a : quest2.getAdventures())
		{
			if (i == 1)
			{
				assertEquals(AdventuresForTest.QUEST2_ADVENTURE1.getAdventureID(),
						a.getAdventureID());
				assertEquals(
						AdventuresForTest.QUEST2_ADVENTURE1.getAdventureDescription(),
						a.getAdventureDescription());
			}
			if (i == 2)
			{
				assertEquals(AdventuresForTest.QUEST2_ADVENTURE2.getAdventureID(),
						a.getAdventureID());
				assertEquals(
						AdventuresForTest.QUEST2_ADVENTURE2.getAdventureDescription(),
						a.getAdventureDescription());
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
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testAddQuests() throws IllegalQuestChangeException
	{
		Player p = playerManager.addPlayer(1);
		QuestState quest = new QuestState(1, 15, QuestStateEnum.AVAILABLE, false);
		QuestManager.getSingleton().addQuestState(p.getPlayerID(), quest);

		QuestState questStateByID = QuestManager.getSingleton()
				.getQuestStateByID(1, 15);
		assertEquals(QuestStateEnum.AVAILABLE, questStateByID.getStateValue());
		assertFalse(questStateByID.isNeedingNotification());

	}

	/**
	 * Make sure quest is triggered if it walks onto a location that has a quest
	 * 
	 * @throws DatabaseException
	 *             QuestManager works with DB
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testPlayerTriggerOnMovement() throws DatabaseException, IllegalQuestChangeException
	{
		Position pos1 = new Position(1, 1);
		Position pos2 = QuestsForTest.THE_LITTLE_QUEST.getPosition();
		Player p = playerManager.addPlayer(4);
		p.setMapName(QuestsForTest.THE_LITTLE_QUEST.getMapName());
		p.setPlayerPosition(pos1);
		QuestManager one = QuestManager.getSingleton();
		assertEquals(
				QuestStatesForTest.PLAYER4_QUEST4.getState(),
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),QuestsForTest.THE_LITTLE_QUEST.getQuestID())
						.getStateValue());
		QuestManager two = QuestManager.getSingleton();
		assertSame(one,two);
		System.out.println(one);
		p.setPlayerPosition(pos2);
		
		assertEquals(
				QuestStateEnum.TRIGGERED,
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),
								QuestsForTest.THE_LITTLE_QUEST.getQuestID())
						.getStateValue());
	}

	/**
	 * Make sure quest is triggered within player
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 * @throws DatabaseException the state changed illegally
	 */
	@Test
	public void testPlayerTriggersQuest() throws IllegalAdventureChangeException, IllegalQuestChangeException, DatabaseException
	{
		Player p = playerManager.addPlayer(7);
		assertEquals(
				QuestStateEnum.AVAILABLE,
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),
								QuestStatesForTest.PLAYER7_QUEST2.getQuestID())
						.getStateValue());

		QuestManager.getSingleton().triggerQuest(7,
				QuestStatesForTest.PLAYER7_QUEST2.getQuestID());
		assertEquals(
				QuestStateEnum.TRIGGERED,
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),
								QuestStatesForTest.PLAYER7_QUEST2.getQuestID())
						.getStateValue());
	}

	/**
	 * When a player moves to the right place, we should trigger the quest
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void triggersOnPlayerMovement() throws IllegalQuestChangeException
	{
		Player p = playerManager.addPlayer(1);
		p.setPlayerPosition(QuestsForTest.ONE_BIG_QUEST.getPosition());
		assertEquals(
				QuestStateEnum.FINISHED,
				QuestManager
						.getSingleton()
						.getQuestStateByID(p.getPlayerID(),
								QuestStatesForTest.PLAYER1_QUEST1.getQuestID())
						.getStateValue());
	}
	
	/**
	 * When a player moves to the right place, we should get a list of adventures
	 * completed at that location
	 * @throws IllegalQuestChangeException the state changed illegally
	 * @throws DatabaseException the state changed illegally 
	 */
	@Test
	public void getCompletedAdventuresOnPlayerMovement() throws IllegalQuestChangeException, DatabaseException
	{
		GameLocation location = (GameLocation)(AdventuresForTest.QUEST2_ADVENTURE2.getCompletionCriteria());
		String mapName = location.getMapName();
		Position pos = location.getPosition();
		
		ArrayList<AdventureRecord> list = QuestManager.getSingleton().getAdventuresByPosition(pos, mapName);
		assertEquals(AdventuresForTest.QUEST2_ADVENTURE2.getAdventureDescription(),list.get(0).getAdventureDescription());
	}
	
	/**
	 * When we add a quest state to a player, we should tell it which player it
	 * belongs to
	 */
	@Test
	public void setPlayerIDWhenQuestStateAdded()
	{
		QuestState questState = new QuestState(4, 1, QuestStateEnum.AVAILABLE, false);
		QuestManager.getSingleton().addQuestState(4, questState);
		assertEquals(4, questState.getPlayerID());
	}

	/**
	 * We should be able to clear out all of the quest states for a given player
	 */
	@Test
	public void canRemoveAPlayersQuestStates()
	{
		QuestManager.getSingleton().addQuestState(4,
				new QuestState(4, 1, QuestStateEnum.AVAILABLE, false));
		QuestManager.getSingleton().removeQuestStatesForPlayer(4);
		assertNull(QuestManager.getSingleton().getQuestList(4));
	}
	
	
	/**
	 * We should be able to clear out all of the quest states for a given player
	 */
	@Test
	public void removeAPlayersQuestStatesWhenLeaves()
	{
		QuestManager.getSingleton().addQuestState(4,
				new QuestState(4, 1, QuestStateEnum.AVAILABLE, false));
		QualifiedObservableConnector.getSingleton().sendReport(new PlayerLeaveReport(4));
		assertNull(QuestManager.getSingleton().getQuestList(4));
	}
	/**
	 * Should be able to change the state of a quest to fulfilled if enough 
	 * adventures are completed. Player 4 quest 3 in the mock data has enough
	 * adventures to be fulfilled, but still looks pending.  We just want to test
	 * the behavior of checking for fulfillment (without an associated adventure 
	 * state change)
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 */
	@Test
	public void testFulfillQuest() throws DatabaseException, IllegalQuestChangeException 
	{
		int playerID = PlayersForTest.JOSH.getPlayerID();
		int questID = 3;
		Player p = playerManager.addPlayer(playerID);
		int initialExp = p.getExperiencePoints();
		assertEquals(initialExp, PlayersForTest.JOSH.getExperiencePoints());
		int expGain = QuestManager.getSingleton().getQuest(questID).getExperiencePointsGained();
		
		QuestState x = QuestManager.getSingleton().getQuestStateByID(playerID, questID);
		x.checkForFulfillment();
		assertEquals(QuestStateEnum.FULFILLED, x.getStateValue());
		assertEquals(initialExp + expGain, p.getExperiencePoints());
	}
	
	/**
	 * This test makes sure that, at the moment an adventure completes, the quest state
	 * changes to fulfilled and the experience points are updated when that is appropriate
	 * @throws DatabaseException shouldn't
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 * 
	 */
	@Test
	public void testCompletingAdventure() throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		int playerID = PlayersForTest.JOSH.getPlayerID();
		int questID = 4;
		Player p = playerManager.addPlayer(playerID);
		int initialExp = p.getExperiencePoints();
		
		AdventureState as = new AdventureState(2, AdventureStateEnum.TRIGGERED, false);
		
		ArrayList<AdventureState> adventures = new ArrayList<AdventureState>();
		adventures.add(as);
		QuestState qs = new QuestState(playerID, questID, QuestStateEnum.FULFILLED, false);
		qs.addAdventures(adventures);
		QuestManager.getSingleton().addQuestState(playerID, qs);
		int expGain = QuestManager.getSingleton().getQuest(questID).getAdventures().get(1).getExperiencePointsGained();
		as.complete();
		assertEquals(initialExp+expGain, p.getExperiencePoints());
	}
	
	/**
	 * Tests that we finishing a quest changes its state to finished
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 * @throws DatabaseException the state changed illegally
	 */
	@Test
	public void testFinishQuest() throws IllegalQuestChangeException, DatabaseException
	{
		int playerID = 2;
		int questID = 4;
		Player p = playerManager.addPlayer(playerID);
		QuestState qs = QuestManager.getSingleton().getQuestStateByID(playerID, questID);
		qs.setPlayerID(playerID);
		
		QuestManager.getSingleton().finishQuest(p.getPlayerID(), qs.getID());
		
		assertEquals(QuestStateEnum.FINISHED, qs.getStateValue());
	}
	
	/**
	 * @throws DatabaseException shouldn't
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 *  
	 */
	@Test
	public void testCompleteAdventure() throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		int playerID = 1;
		int questID = 3;
		int adventureID = 1;
		
		Player p = playerManager.addPlayer(playerID);
		QuestState qs = QuestManager.getSingleton().getQuestStateByID(p.getPlayerID(), questID);
		
		AdventureState as = QuestManager.getSingleton().getAdventureStateByID(p.getPlayerID(), questID, adventureID);
		
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		
		adventureList.add(as);
		
		qs.addAdventures(adventureList);
		
		qs.setPlayerID(playerID);
		
		QuestManager.getSingleton().completeAdventure(playerID, questID, adventureID);
		
		assertEquals(AdventureStateEnum.COMPLETED, QuestManager.getSingleton().getAdventureStateByID(playerID, questID, adventureID).getState());
	}
	
	/**
	 * When a player moves to the right place, should complete adventure
	 * @throws IllegalQuestChangeException the state changed illegally
	 * @throws DatabaseException 
	 */
	@Test
	public void completeAdventureOnPlayerMovement() throws IllegalQuestChangeException, DatabaseException
	{
		//We need to add a quest and adventure that are triggered on location but not already completed
		int questID = AdventuresForTest.QUEST2_ADVENTURE2.getQuestID();
		int advID = AdventuresForTest.QUEST2_ADVENTURE2.getAdventureID();
		
		GameLocation location = (GameLocation)(AdventuresForTest.QUEST2_ADVENTURE2.getCompletionCriteria());
		Position pos = location.getPosition();
		Player paul = playerManager.addPlayer(8);
		paul.setPlayerPosition(pos);
		assertEquals(AdventureStateEnum.COMPLETED, QuestManager.getSingleton().getAdventureStateByID(paul.getPlayerID(),
				questID, advID).getState());
	}

}
