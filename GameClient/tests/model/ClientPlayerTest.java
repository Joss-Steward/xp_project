package model;

import static org.junit.Assert.*;
import model.ClientPlayer;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.Position;
import datasource.DatabaseException;

/**
 * Tests the player class
 * 
 * @author merlin
 * 
 */
public class ClientPlayerTest
{

	/**
	 * Make sure that players get initialized correctly.
	 */
	@Test
	public void initialization()
	{
		ClientPlayer p = new ClientPlayer(1);
		assertEquals(1, p.getID());
	}

	/**
	 * Simple test for all setters and getters
	 */
	@Test
	public void testSettersGetters()
	{
		ClientPlayer p = new ClientPlayer(1);
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
		EqualsVerifier.forClass(ClientPlayer.class).verify();
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
		ClientPlayer p = new ClientPlayer(1);
		Position pos = new Position(3, 3);
		p.move(pos);
		assertEquals(pos, p.getPosition());
	}
}
