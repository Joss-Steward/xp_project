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
		adventures.add(new Adventure("Merlin Zone"));
		adventures.add(new Adventure("Library Quest"));
		
		Quest q = new Quest("I am a description", adventures);
		
		assertEquals("I am a description", q.getDescription());
		assertNotNull(q.getAdventures().contains(adventures));
	}

	/**
	 * Test the setters for setting description and state of quests
	 */
	@Test
	public void testSetters()
	{
		Quest q = new Quest(null, null);
		ArrayList<Adventure> adventures = new ArrayList<Adventure>();
		adventures.add(new Adventure("Merlin Zone"));
		adventures.add(new Adventure("Library Quest"));
		
		q.setDescription("I am set");
		q.setAdventures(adventures);
		
		assertEquals("I am set", q.getDescription());
		assertNotNull(q.getAdventures().contains(adventures));
	}
}
