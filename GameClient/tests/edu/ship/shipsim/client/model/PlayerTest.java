package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.Position;
import datasource.DatabaseException;
import edu.ship.shipsim.client.model.Player;

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
		assertEquals(1, p.getID());
	}

	/**
	 * Simple test for all setters and getters
	 */
	@Test
	public void testSettersGetters()
	{
		Player p = new Player(1);
		p.setName("name");
		p.setAppearanceType("type");

		assertEquals("name", p.getName());
		assertEquals("type", p.getAppearanceType());
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
	 * 
	 * @throws DatabaseException
	 *             shouldn'ts
	 */
	@Test
	public void testPlayerPosition() throws DatabaseException
	{
		Player p = new Player(1);
		Position pos = new Position(3, 3);
		p.move(pos);
		assertEquals(pos, p.getPosition());
	}
}
