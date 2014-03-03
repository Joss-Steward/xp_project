package model;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.Position;

/**
 * Tests the player class
 * 
 * @author merlin
 * 
 */
public class PlayerTest
{

	/**
	 * Make sure that players get initialized correctly.
	 */
	@Test
	public void initialization()
	{
		Player p = new Player(1);
		assertEquals(1, p.getPlayerID());
	}

	/**
	 * Check the equals method
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(Player.class).verify();
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
		p.move(pos);
		assertEquals(pos, p.getPlayerPosition());
	}
}
