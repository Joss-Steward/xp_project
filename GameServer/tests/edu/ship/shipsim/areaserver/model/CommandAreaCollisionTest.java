package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests that CommandAreaCollision triggers a quest on a player.
 * 
 * @author Ethan
 *
 */
public class CommandAreaCollisionTest {

	/**
	 * Tests that a CommandAreaCollision when executed will 
	 * trigger a quest that a player has.
	 */
	@Test
	public void testCreateCommand() {
		
		int playerID = 1;
		String areaName = "test";
		CommandAreaCollision cac = new CommandAreaCollision(playerID, areaName);
		cac.execute();
		
		assertEquals(playerID, cac.getPlayerID());
		assertEquals(areaName, cac.getAreaName());
	}

}
