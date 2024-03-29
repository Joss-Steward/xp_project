package model;

import static org.junit.Assert.*;
import model.ClientPlayer;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

import datasource.DatabaseException;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;

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
		p.setCrew(Crew.NULL_POINTER);
		p.setMajor(Major.ELECTRICAL_ENGINEERING);

		assertEquals("name", p.getName());
		assertEquals("type", p.getAppearanceType());
		assertEquals(Crew.NULL_POINTER, p.getCrew());
		assertEquals(Major.ELECTRICAL_ENGINEERING, p.getMajor());
	}

	/**
	 * Check the equals method
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(ClientPlayer.class).suppress(Warning.NONFINAL_FIELDS).verify();
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
