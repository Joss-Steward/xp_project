package model;

import static org.junit.Assert.*;

import org.junit.Test;

import data.AdventureStateEnum;
import data.QuestStateEnum;

/**
 * Tests the basic ClientPlayerAdventure class and its functionality. 
 * @author Nathaniel
 *
 */
public class ClientPlayerAdventureTest 
{
	/**
	 * Test the initialization of ClientPlayerAdventure
	 */
	@Test
	public void testClientPlayerAdventureInitializaiton() 
	{
		ClientPlayerAdventure a = new ClientPlayerAdventure(1, "Test Adventure", 3, AdventureStateEnum.HIDDEN, false, true, "Dept chair",QuestStateEnum.AVAILABLE);
		assertEquals(1, a.getAdventureID());
		assertEquals("Test Adventure", a.getAdventureDescription());
		assertEquals(3, a.getAdventureXP());
		assertEquals(AdventureStateEnum.HIDDEN, a.getAdventureState());
		assertTrue(a.isRealLifeAdventure());
		assertEquals("Dept chair", a.getWitnessTitle());
	}
}
