package model;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
