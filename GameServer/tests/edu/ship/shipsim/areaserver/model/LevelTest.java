package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for the level object in the model
 * @author Merlin
 *
 */
public class LevelTest
{

	/**
	 * Test initializing a level
	 */
	@Test
	public void testInit()
	{
		Level l = new Level("Level Description", 42);
		assertEquals("Level Description", l.getDescription());
		assertEquals(42, l.getLevelUpPoints());
	}

}
