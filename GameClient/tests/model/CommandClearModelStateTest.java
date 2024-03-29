package model;

import static org.junit.Assert.*;
import model.CommandClearModelState;
import model.MapManager;
import model.ClientPlayerManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test that the CommandClearModelState clears the players and map properly
 * @author Steve
 *
 */
public class CommandClearModelStateTest 
{
	/**
	 * Reset the MapManager after we're done.
	 */
	@After
	@Before
	public void cleanup()
	{
		MapManager.resetSingleton();
		ClientPlayerManager.resetSingleton();
	}
	
	/**
	 * Ensure that the players model is reset
	 */
	@Test
	public void testDoesResetPlayers()
	{
		ClientPlayerManager original = ClientPlayerManager.getSingleton();
		
		CommandClearModelState cms = new CommandClearModelState();
		assertTrue(cms.execute());

		assertNotEquals(original, ClientPlayerManager.getSingleton());
	}
	
	/**
	 * Ensure that the current map is reset
	 */
	@Test
	public void testDoesResetMap()
	{
		MapManager original = MapManager.getSingleton();
		
		CommandClearModelState cms = new CommandClearModelState();
		assertTrue(cms.execute());
		
		assertNotEquals(original, MapManager.getSingleton());
	}
}
