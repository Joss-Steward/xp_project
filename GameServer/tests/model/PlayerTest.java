package model;

import static org.junit.Assert.*;

import org.junit.Test;

import data.Position;

/**
 * Test the Player classs
 * @author Merlin
 *
 */
public class PlayerTest
{

	/** 
	 * Make sure we can retrieve a player's unique name from the db
	 * @throws DatabaseException shouldn'ts
	 */
	@Test
	public void canGetPlayerName() throws DatabaseException
	{
		Player p = new Player(1);
		assertEquals("John", p.getPlayerName());
	}
	
	/**
	 * Sets the players position and checks it
	 * @throws DatabaseException shouldn'ts
	 */
	@Test
	public void testPlayerPosition() throws DatabaseException
	{
		Player p = new Player(1);
		Position pos = new Position(3, 3);
		p.setPlayerPosition(pos);
		assertEquals(pos, p.getPlayerPosition());
		
		//Test for null
		Player c = new Player(2);
		assertEquals(null, c.getPlayerPosition());
	}

}
