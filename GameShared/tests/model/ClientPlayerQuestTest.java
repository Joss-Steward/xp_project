package model;

import static org.junit.Assert.*;

import org.junit.Test;

import datasource.AdventureStateList;
import datasource.QuestStateList;

/**
 * Test the 
 * @author nk3668
 *
 */
public class ClientPlayerQuestTest 
{

	/**
	 * Tests that a clients player will contain state, id, and a description.
	 */
	@Test
	public void testClientPlayerQuestInitialization() 
	{
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateList.HIDDEN);
		assertEquals(1, q.getQuestID());
		assertEquals("Test Quest 1", q.getQuestDescription());
		assertEquals(QuestStateList.HIDDEN, q.getQuestState());
	}

	
	/**
	 * Tests the ability to add an adventure to the quest
	 */
	@Test
	public void testAddingAdventures() 
	{
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", AdventureStateList.HIDDEN);
		assertEquals(1, adventureOne.getAdventureID());
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", AdventureStateList.HIDDEN);
		assertEquals(2, adventureTwo.getAdventureID());
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateList.HIDDEN);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		assertEquals(1, q.getAdventureList().get(0).getAdventureID());
		assertEquals(2, q.getAdventureList().get(1).getAdventureID());
	}
}
