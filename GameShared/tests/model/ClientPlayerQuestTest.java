package model;

import static org.junit.Assert.*;

import org.junit.Test;

import data.AdventureStateEnum;
import data.QuestStateEnum;

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
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.HIDDEN, 42, 133, true);
		assertEquals(1, q.getQuestID());
		assertEquals("Test Quest 1", q.getQuestDescription());
		assertEquals(QuestStateEnum.HIDDEN, q.getQuestState());
		assertEquals(42, q.getExperiencePointsGained());
		assertEquals(133, q.getAdventuresToFulfillment());
	}

	/**
	 * Create a new ClientPlayerQuest with two ClientPlayerAdventures
	 * @return the ClientPlayerQuest
	 */
	public static ClientPlayerQuest createOneQuestWithTwoAdventures()
	{
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", 4, AdventureStateEnum.HIDDEN, false);
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", 4, AdventureStateEnum.HIDDEN, false);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.HIDDEN, 1, 2, true);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		return q;
	}
	
	/**
	 * Create a new ClientPlayerQuest with two ClientPlayerAdventures
	 * @return the ClientPlayerQuest
	 */
	public static ClientPlayerQuest createOneQuestWithTwoAdventuresNeedingNotification()
	{
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", 4, AdventureStateEnum.COMPLETED, true);
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", 2, AdventureStateEnum.COMPLETED, true);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.FINISHED, 1, 2, true);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		return q;
	}
	
	/**
	 * Tests the ability to add an adventure to the quest
	 */
	@Test
	public void testAddingAdventures() 
	{
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", 10, AdventureStateEnum.HIDDEN, false);
		assertEquals(1, adventureOne.getAdventureID());
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", 7, AdventureStateEnum.HIDDEN, false);
		assertEquals(2, adventureTwo.getAdventureID());
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.HIDDEN, 1, 2, true);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		assertEquals(1, q.getAdventureList().get(0).getAdventureID());
		assertEquals(2, q.getAdventureList().get(1).getAdventureID());
	}
}
