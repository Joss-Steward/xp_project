package model;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * This class holds the tests for the Quest class
 * 
 * @author Scott Lantz, LaVonne Diller
 *
 */
public class QuestModelTest {

	/**
	 * Test the initialization of Quests and its parameters
	 */
	@Test
	public void testInitialize() 
	{
		ArrayList<Adventure> adventures = new ArrayList<Adventure>();
		adventures.add(new Adventure("Merlin Zone", "Available"));
		adventures.add(new Adventure("Library Quest", "Hidden"));
		
		Quest q = new Quest("I am a description","Hidden", adventures);
		
		assertEquals("I am a description", q.getDescription());
		assertEquals("Hidden", q.getState());
		assertNotNull(q.getAdventures().contains(adventures));
	}

	/**
	 * Test the setters for setting description and state of quests
	 */
	@Test
	public void testSetters()
	{
		Quest q = new Quest(null, null, null);
		ArrayList<Adventure> adventures = new ArrayList<Adventure>();
		adventures.add(new Adventure("Merlin Zone", "Available"));
		adventures.add(new Adventure("Library Quest", "Hidden"));
		
		q.setDescription("I am set");
		q.setState("complete");
		q.setAdventures(adventures);
		
		assertEquals("I am set", q.getDescription());
		assertEquals("complete", q.getState());	
		assertNotNull(q.getAdventures().contains(adventures));
	}
}
