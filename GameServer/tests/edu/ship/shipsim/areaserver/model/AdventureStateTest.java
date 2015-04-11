package edu.ship.shipsim.areaserver.model;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import datasource.AdventureStateEnum;
import edu.ship.shipsim.areaserver.datasource.AdventureStatesForTest;
import edu.ship.shipsim.areaserver.model.AdventureState;


/**
 * Test class for AdventureState
 * @author Ryan
 *
 */
public class AdventureStateTest {

	QuestState questState = null;
	/**
	 * Test to ensure the creation of an adventure is correct
	 */
	@Test
	public void testInitialization()
	{
		AdventureState adventure = new AdventureState(questState, 1, AdventureStateEnum.HIDDEN);
		
		assertEquals(1, adventure.getID());
		assertEquals(AdventureStateEnum.HIDDEN, adventure.getState());
		
	}
	
	/**
	 * Test to check if an adventure's state can be changed from hidden to pending using the trigger method
	 */
	@Test
	public void testTriggerAdventure() 
	{
		AdventureState adventure = new AdventureState(questState, 1, AdventureStateEnum.HIDDEN);
		adventure.trigger();
		assertEquals(AdventureStateEnum.PENDING, adventure.getState());
	}
	
	/**
	 * Test trigger when the adventure's state is not initially hidden
	 */
	@Test
	public void testTriggerNonHiddenAdventure() 
	{
		AdventureState adventure = new AdventureState(questState, 1, AdventureStateEnum.COMPLETED);
		adventure.trigger();
		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
	}
	@Ignore
	@Test
	public void testCompleteAdventure()
	{
		AdventureStatesForTest adventure = AdventureStatesForTest.PLAYER1_QUEST1_ADV1;
		fail("How do I set up this test?");
	}
}
