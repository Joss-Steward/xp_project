package data;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import data.Position;

/**
 * Test the Position class
 * 
 * @author merlin
 * 
 */
public class PositionTest {

	/**
	 * It just holds some data
	 */
	@Test
	public void initialization() {
		Position p = new Position(42, 47);
		assertEquals(42, p.getRow());
		assertEquals(47, p.getColumn());
	}

	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(Position.class).verify();
	}
}
