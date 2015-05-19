package view.screen.popup;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for adventure complete behavior
 * @author Ryan
 *
 */
public class AdventureCompleteBehaviorTest 
{

	/**
	 * Test the getters and initialization of the behavior
	 */
	@Test
	public void testInitialization() 
	{
		AdventureCompleteBehavior behavior = new AdventureCompleteBehavior(1, 1, 1);
		
		assertEquals(1, behavior.getPlayerID());
		assertEquals(1, behavior.getQuestID());
		assertEquals(1, behavior.getAdventureID());
		
	}

}
