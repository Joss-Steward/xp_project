package model;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

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
}
