package edu.ship.shipsim.areaserver.model;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import data.Position;
import edu.ship.shipsim.areaserver.model.Adventure;
import edu.ship.shipsim.areaserver.model.Quest;

/**
 * This class holds the tests for the Quest class
 * 
 * @author Scott Lantz, LaVonne Diller
 *
 */
public class QuestTest {

	/**
	 * Test the initialization of Quests and its parameters
	 */
	@Test
	public void testInitialize() 
	{
		ArrayList<Adventure> adventures = new ArrayList<Adventure>();
		adventures.add(new Adventure(42,"Merlin Zone"));
		adventures.add(new Adventure(420,"Library Quest"));
		
		Position pos = new Position(33,44);
		
		Quest q = new Quest(245,"I am a description","HappyZone",pos, adventures, 42, 13);
		
		assertEquals(245,q.getQuestID());
		assertEquals("I am a description", q.getDescription());
		assertEquals("HappyZone",q.getMapName());
		assertEquals(pos,q.getPos());
		assertNotNull(q.getAdventures().contains(adventures));
		assertEquals(42, q.getExperiencePointsGained());
		assertEquals(13, q.getAdventuresForFulfillment());
	}

	/**
	 * Test the setters for setting description and state of quests
	 */
	@Test
	public void testSetters()
	{
		Quest q = new Quest(-1, null,null,null,null, 42, 45);
		ArrayList<Adventure> adventures = new ArrayList<Adventure>();
		adventures.add(new Adventure(42,"Merlin Zone"));
		adventures.add(new Adventure(420,"Library Quest"));
		Position pos = new Position(22,20);
		
		q.setQuestID(44);
		q.setDescription("I am set");
		q.setMapName("Map Name");
		q.setPos(pos);
		q.setAdventures(adventures);
		
		assertEquals(44,q.getQuestID());
		assertEquals("I am set", q.getDescription());
		assertEquals("Map Name", q.getMapName());
		assertEquals(pos, q.getPos());
		assertNotNull(q.getAdventures().contains(adventures));
	}
}
