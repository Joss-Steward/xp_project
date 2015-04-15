package model;

import static org.junit.Assert.*;

import org.junit.Test;

import datasource.AdventureStateEnum;

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
		ClientPlayerAdventure a = new ClientPlayerAdventure(1, "Test Adventure", AdventureStateEnum.HIDDEN, false);
		assertEquals(1, a.getAdventureID());
		assertEquals("Test Adventure", a.getAdventureDescription());
		assertEquals(AdventureStateEnum.HIDDEN, a.getAdventureState());
	}
}
