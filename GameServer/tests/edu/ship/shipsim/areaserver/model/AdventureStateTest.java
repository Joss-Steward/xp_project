package edu.ship.shipsim.areaserver.model;
import static org.junit.Assert.*;

import org.junit.Test;

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
		AdventureState adventure = new AdventureState(1, "hidden");
		
		assertEquals(1, adventure.getID());
		assertEquals("hidden", adventure.getState());
		
	}
	
	/**
	 * Test to check if an adventure's state can be changed from hidden to pending using the trigger method
	 */
	@Test
	public void testTriggerAdventure() 
	{
		AdventureState adventure = new AdventureState(1, "hidden");
		adventure.trigger();
		assertEquals("pending", adventure.getState());
	}
	
	/**
	 * Test trigger when the adventure's state is not initially hidden
	 */
	@Test
	public void testTriggerNonHiddenAdventure() 
	{
		AdventureState adventure = new AdventureState(1, "complete");
		adventure.trigger();
		assertEquals("complete", adventure.getState());
	}
}
