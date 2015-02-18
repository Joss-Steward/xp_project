package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandAreaCollisionTest {

	@Test
	public void testCreateCommand() {
		
		//Add to all tests
		
		int playerID = 1;
		String areaName = "test";
		CommandAreaCollision cac = new CommandAreaCollision(playerID, areaName);
		cac.execute();
		
		assertEquals(playerID, cac.getPlayerID());
		assertEquals(areaName, cac.getAreaName());
	}

}
