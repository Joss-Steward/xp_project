package edu.ship.shipsim.areaserver.model;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.ship.shipsim.areaserver.model.Adventure;
import edu.ship.shipsim.areaserver.model.Quest;

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
		adventures.add(new Adventure(42,"Merlin Zone"));
		adventures.add(new Adventure(420,"Library Quest"));
		
		Quest q = new Quest(245,"I am a description", adventures);
		
		assertEquals(245,q.getQuestID());
		assertEquals("I am a description", q.getDescription());
		assertNotNull(q.getAdventures().contains(adventures));
	}

	/**
	 * Test the setters for setting description and state of quests
	 */
	@Test
	public void testSetters()
	{
		Quest q = new Quest(-1, null, null);
		ArrayList<Adventure> adventures = new ArrayList<Adventure>();
		adventures.add(new Adventure(42,"Merlin Zone"));
		adventures.add(new Adventure(420,"Library Quest"));
		
		q.setQuestID(44);
		q.setDescription("I am set");
		q.setAdventures(adventures);
		
		assertEquals(44,q.getQuestID());
		assertEquals("I am set", q.getDescription());
		assertNotNull(q.getAdventures().contains(adventures));
	}
}
