package model;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class PlayerIDTest
{

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(PlayerID.class).verify();
	}

	/**
	 * The toString should just return the name
	 */
	@Test
	public void toStringTest()
	{
		assertEquals("Fred", new PlayerID(3, "Fred").toString());
	}

	/**
	 * tests that comparability is based on player name - not player id
	 */
	@Test
	public void comparability()
	{
		PlayerID one = new PlayerID(3, "Anna");
		PlayerID two = new PlayerID(1, "Wellington");
		assertTrue(one.compareTo(two) < 0);
		assertTrue(one.compareTo(one) == 0);
		assertTrue(two.compareTo(one) > 0);
	}
}
