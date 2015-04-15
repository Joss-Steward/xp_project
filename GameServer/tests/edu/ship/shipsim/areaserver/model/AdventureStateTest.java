package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import datasource.AdventureStateEnum;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.QuestStateEnum;

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
		OptionsManager.getSingleton(true);
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
	 */
	@Test
	public void testTriggerAdventure()
	{
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.HIDDEN, false);
		adventure.trigger();
		assertEquals(AdventureStateEnum.PENDING, adventure.getState());
	}

	/**
	 * Test trigger when the adventure's state is not initially hidden
	 */
	@Test
	public void testTriggerNonHiddenAdventure()
	{
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.COMPLETED, false);
		adventure.trigger();
		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
	}

	/**
	 * Completing an adventure doesn't make the quest fulfilled unless we have
	 * completed enough of them
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testCompleteNotFulfillingAdventure() throws DatabaseException
	{
		questState = new QuestState(1, QuestStateEnum.TRIGGERED);
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.PENDING, false);
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		adventureList.add(adventure);
		questState.addAdventures(adventureList);
		adventure.complete();
		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
		assertTrue(adventure.isNeedingNotification());
		assertEquals(QuestStateEnum.TRIGGERED, questState.getStateValue());
	}

	/**
	 * When we complete the right number of adventures, the quest should move to
	 * the state that will cause fulfillment notification to occur
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testCompleteFulfillingAdventure() throws DatabaseException
	{
		questState = new QuestState(2, QuestStateEnum.TRIGGERED);
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.PENDING, false);
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		adventureList.add(adventure);
		questState.addAdventures(adventureList);

		adventure.complete();
		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
		assertTrue(adventure.isNeedingNotification());
		assertEquals(QuestStateEnum.NEED_FULFILLED_NOTIFICATION,
				questState.getStateValue());
	}

	/**
	 * We can't fulfill a quest that is already fulfilled
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testCompleteAlreadyFulfilledAdventure() throws DatabaseException
	{
		questState = new QuestState(2, QuestStateEnum.FULFILLED);
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.COMPLETED, false);
		AdventureState adventure2 = new AdventureState(2, AdventureStateEnum.PENDING, false);
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		adventureList.add(adventure);
		adventureList.add(adventure2);
		questState.addAdventures(adventureList);

		adventure2.complete();
		assertEquals(AdventureStateEnum.COMPLETED, adventure2.getState());
		assertTrue(adventure2.isNeedingNotification());
		assertEquals(QuestStateEnum.FULFILLED, questState.getStateValue());
	}
}
