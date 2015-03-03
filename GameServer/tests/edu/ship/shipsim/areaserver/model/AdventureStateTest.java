package edu.ship.shipsim.areaserver.model;
import static org.junit.Assert.*;

import org.junit.Test;

import datasource.AdventureStateList;
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
		AdventureState adventure = new AdventureState(1, AdventureStateList.HIDDEN);
		
		assertEquals(1, adventure.getID());
		assertEquals(AdventureStateList.HIDDEN, adventure.getState());
		
	}
	
	/**
	 * Test to check if an adventure's state can be changed from hidden to pending using the trigger method
	 */
	@Test
	public void testTriggerAdventure() 
	{
		AdventureState adventure = new AdventureState(1, AdventureStateList.HIDDEN);
		adventure.trigger();
		assertEquals(AdventureStateList.PENDING, adventure.getState());
	}
	
	/**
	 * Test trigger when the adventure's state is not initially hidden
	 */
	@Test
	public void testTriggerNonHiddenAdventure() 
	{
		AdventureState adventure = new AdventureState(1, AdventureStateList.COMPLETED);
		adventure.trigger();
		assertEquals(AdventureStateList.COMPLETED, adventure.getState());
	}
}
