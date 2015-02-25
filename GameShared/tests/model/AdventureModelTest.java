package model;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class holds the tests for the Adventure class
 * 
 * @author Scott Lantz, LaVonne Diller
 *
 */

public class AdventureModelTest 
{
	/**
	 * Initializes the Adventure class and ensures
	 *  all fields hold appropriate values
	 */
	@Test
	public void Initialize() {
		Adventure a = new Adventure(1, "I am a description");
		
		assertEquals(1, a.getID());
		assertEquals("I am a description", a.getDescription());
	}
	
	/**
	 *Tests to ensure setters function within the class
	 */
	@Test
	public void testSetters()
	{
		Adventure a = new Adventure(4,null);
		a.setID(55);
		a.setDescription("I am set");
		
		assertEquals(55,a.getID());
		assertEquals("I am set", a.getDescription());	
	}
}
