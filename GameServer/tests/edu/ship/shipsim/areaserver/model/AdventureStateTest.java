package edu.ship.shipsim.areaserver.model;
import static org.junit.Assert.*;

import org.junit.Test;

import datasource.AdventureStateEnum;
import edu.ship.shipsim.areaserver.model.AdventureState;


/**
 * Test class for AdventureState
 * @author Ryan
 *
 */
public class AdventureStateTest {

	/**
	 * Test to ensure the creation of an adventure is correct
	 */
	@Test
	public void testInitialization()
	{
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.HIDDEN);
		
		assertEquals(1, adventure.getID());
		assertEquals(AdventureStateEnum.HIDDEN, adventure.getState());
		
	}
	
	/**
	 * Test to check if an adventure's state can be changed from hidden to pending using the trigger method
	 */
	@Test
	public void testTriggerAdventure() 
	{
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.HIDDEN);
		adventure.trigger();
		assertEquals(AdventureStateEnum.PENDING, adventure.getState());
	}
	
	/**
	 * Test trigger when the adventure's state is not initially hidden
	 */
	@Test
	public void testTriggerNonHiddenAdventure() 
	{
		AdventureState adventure = new AdventureState(1, AdventureStateEnum.COMPLETED);
		adventure.trigger();
		assertEquals(AdventureStateEnum.COMPLETED, adventure.getState());
	}
}
