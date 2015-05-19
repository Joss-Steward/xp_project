package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.OptionsManager;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datasource.AdventureStateEnum;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.QuestStateEnum;
import edu.ship.shipsim.areaserver.datasource.AdventureStatesForTest;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;
import edu.ship.shipsim.areaserver.datasource.QuestStatesForTest;
import edu.ship.shipsim.areaserver.datasource.QuestsForTest;
import edu.ship.shipsim.areaserver.model.reports.AdventureStateChangeReport;

/**
 * Test class for AdventureState
 * 
 * @author Ryan
 *
 */
public class AdventureStateTest extends DatabaseTest
{

	private QuestState questState = null;

	/**
	 * 
	 */
	@Before
	public void setUp()
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton().setTestMode(true);
		QuestManager.resetSingleton();
	}

	/**
	 * Test to ensure the creation of an adventure is correct
	 */
	@Test
	public void testInitialization()
	{
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.HIDDEN, false);

		assertEquals(1, adventure.getID());
		assertEquals(AdventureStateEnum.HIDDEN, adventure.getState());

	}

	/**
	 * Test to check if an adventure's state can be changed from hidden to
	 * pending using the trigger method
	 * 
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 */
	@Test
	public void testTriggerAdventure() throws IllegalAdventureChangeException,
			DatabaseException, IllegalQuestChangeException
	{
		int playerID = 1;
		AdventureState adv = new AdventureState(1, AdventureStateEnum.HIDDEN, false);
		ArrayList<AdventureState> al = new ArrayList<AdventureState>();
		al.add(adv);
		QuestState q = new QuestState(2, QuestStateEnum.TRIGGERED, false);
		q.addAdventures(al);
		q.setPlayerID(playerID);
		QuestManager.getSingleton().addQuestState(playerID, q);
		adv.trigger();
		assertEquals(AdventureStateEnum.TRIGGERED, adv.getState());
	}

	/**
	 * Test trigger when the adventure's state is not initially hidden
	 * 
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 */
	@Test(expected = IllegalAdventureChangeException.class)
	public void testTriggerNonHiddenAdventure() throws IllegalAdventureChangeException,
			DatabaseException, IllegalQuestChangeException
	{
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.COMPLETED,
				false);
		adventure.trigger();
		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
	}

	/**
	 * Completing an adventure doesn't make the quest fulfilled unless we have
	 * completed enough of them
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testCompleteNotFulfillingAdventure() throws DatabaseException,
			IllegalAdventureChangeException, IllegalQuestChangeException
	{

		PlayerManager.getSingleton().addPlayer(2);
		ArrayList<AdventureState> al = new ArrayList<AdventureState>();
		AdventureState adventure = new AdventureState(
				AdventureStatesForTest.PLAYER2_QUEST1_ADV1.getAdventureID(),
				AdventureStatesForTest.PLAYER2_QUEST1_ADV1.getState(),
				AdventureStatesForTest.PLAYER2_QUEST1_ADV1.isNeedingNotification());
		al.add(adventure);
		QuestState qState = new QuestState(
				QuestStatesForTest.PLAYER2_QUEST1.getQuestID(),
				QuestStatesForTest.PLAYER2_QUEST1.getState(),
				QuestStatesForTest.PLAYER2_QUEST1.isNeedingNotification());

		qState.addAdventures(al);
		qState.setPlayerID(2);

		adventure.complete();

		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
		assertFalse(adventure.isNeedingNotification());
		assertEquals(QuestStateEnum.TRIGGERED, qState.getStateValue());
	}

	/**
	 * When we complete the right number of adventures, the quest should move to
	 * the state that will cause fulfillment notification to occur
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testCompleteFulfillingAdventure() throws DatabaseException,
			IllegalAdventureChangeException, IllegalQuestChangeException
	{
		PlayerManager.getSingleton().addPlayer(1);

		questState = new QuestState(2, QuestStateEnum.TRIGGERED, false);

		questState.setPlayerID(1);

		AdventureState adventure = new AdventureState(1, AdventureStateEnum.TRIGGERED,
				false);
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		adventureList.add(adventure);
		for (int i = 0; i < QuestsForTest.THE_OTHER_QUEST.getAdventuresForFulfillment() - 1; i++)
		{
			adventureList.add(new AdventureState(i, AdventureStateEnum.COMPLETED, false));
		}
		questState.addAdventures(adventureList);

		adventure.complete();
		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
		assertFalse(adventure.isNeedingNotification());
		assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
		assertTrue(questState.isNeedingNotification());
	}

	/**
	 * We can't fulfill a quest that is already fulfilled
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException
	 *             thrown if illegal state change
	 */
	@Test
	public void testCompleteAlreadyFulfilledAdventure() throws DatabaseException,
			IllegalAdventureChangeException, IllegalQuestChangeException
	{
		PlayerManager.getSingleton().addPlayer(1);

		questState = new QuestState(2, QuestStateEnum.FULFILLED, false);

		questState.setPlayerID(1);

		AdventureState adventure = new AdventureState(1, AdventureStateEnum.COMPLETED,
				false);
		AdventureState adventure2 = new AdventureState(2, AdventureStateEnum.TRIGGERED,
				false);
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		adventureList.add(adventure);
		adventureList.add(adventure2);
		questState.addAdventures(adventureList);

		adventure2.complete();
		assertEquals(AdventureStateEnum.COMPLETED, adventure2.getState());
		assertFalse(adventure2.isNeedingNotification());
		assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
		assertFalse(questState.isNeedingNotification());
	}

	/**
	 * Test that the new change state method works as intended.
	 * 
	 * @throws IllegalAdventureChangeException
	 *             thrown if changing to a wrong state
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException
	 *             thrown if changing to a wrong state
	 */
	@Test
	public void testChangeStateToPending() throws IllegalAdventureChangeException,
			DatabaseException, IllegalQuestChangeException
	{

		ArrayList<AdventureState> al = new ArrayList<AdventureState>();
		AdventureState adventure = new AdventureState(
				AdventureStatesForTest.PLAYER7_QUEST2_ADV1.getAdventureID(),
				AdventureStatesForTest.PLAYER7_QUEST2_ADV1.getState(),
				AdventureStatesForTest.PLAYER7_QUEST2_ADV1.isNeedingNotification());
		al.add(adventure);
		QuestState qState = new QuestState(
				QuestStatesForTest.PLAYER7_QUEST2.getQuestID(),
				QuestStatesForTest.PLAYER7_QUEST2.getState(),
				QuestStatesForTest.PLAYER7_QUEST2.isNeedingNotification());

		qState.addAdventures(al);
		qState.setPlayerID(1);

		adventure.changeState(AdventureStateEnum.TRIGGERED, false);
		assertEquals(adventure.getState(), AdventureStateEnum.TRIGGERED);
	}

	/**
	 * Makes sure that a AdventureStateChangeReport is created when the
	 * adventures change state from PENDING to COMPLETED
	 * 
	 * @throws IllegalQuestChangeException
	 *             shouldn't throw this
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalAdventureChangeException
	 *             shouldn't either
	 */
	@Test
	public void receiveReportChangeState() throws IllegalAdventureChangeException,
			DatabaseException, IllegalQuestChangeException
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		ArrayList<AdventureState> al = new ArrayList<AdventureState>();
		AdventureState state = new AdventureState(
				AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getAdventureID(),
				AdventureStateEnum.TRIGGERED,
				AdventureStatesForTest.PLAYER1_QUEST2_ADV2.isNeedingNotification());
		al.add(state);
		QuestState qState = new QuestState(
				QuestStatesForTest.PLAYER1_QUEST2.getQuestID(),
				QuestStatesForTest.PLAYER1_QUEST2.getState(),
				QuestStatesForTest.PLAYER1_QUEST2.isNeedingNotification());

		qState.addAdventures(al);
		AdventureStateChangeReport report = new AdventureStateChangeReport(
				AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getPlayerID(),
				AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getQuestID(),
				AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getAdventureID(),
				AdventuresForTest.QUEST2_ADVENTURE2.getAdventureDescription(),
				AdventureStateEnum.COMPLETED);
		qState.setPlayerID(1);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				AdventureStateChangeReport.class);

		obs.receiveReport(EasyMock.eq(report));

		EasyMock.replay(obs);

		state.changeState(AdventureStateEnum.COMPLETED, true);

		EasyMock.verify(obs);
	}
}
