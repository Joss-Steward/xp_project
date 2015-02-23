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
		Adventure a = new Adventure("I am a description","Hidden");
		
		assertEquals("I am a description", a.getDescription());
		assertEquals("Hidden", a.getState());
	}
	
	/**
	 *Tests to ensure setters function within the class
	 */
	@Test
	public void testSetters()
	{
		Adventure a = new Adventure(null, null);
		
		a.setDescription("I am set");
		a.setState("Available");
		
		assertEquals("I am set", a.getDescription());
		assertEquals("Available", a.getState());	
	}
}
