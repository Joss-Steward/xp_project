package model;

import static org.junit.Assert.*;

import org.junit.Test;

import datasource.AdventureStateEnum;
import datasource.QuestStateEnum;

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
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.HIDDEN);
		assertEquals(1, q.getQuestID());
		assertEquals("Test Quest 1", q.getQuestDescription());
		assertEquals(QuestStateEnum.HIDDEN, q.getQuestState());
	}

	/**
	 * Create a new ClientPlayerQuest with two ClientPlayerAdventures
	 * @return the ClientPlayerQuest
	 */
	public static ClientPlayerQuest createOneQuestWithTwoAdventures()
	{
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", AdventureStateEnum.HIDDEN);
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", AdventureStateEnum.HIDDEN);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.HIDDEN);
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
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", AdventureStateEnum.NEED_NOTIFICATION);
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", AdventureStateEnum.NEED_NOTIFICATION);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.FINISHED);
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
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", AdventureStateEnum.HIDDEN);
		assertEquals(1, adventureOne.getAdventureID());
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", AdventureStateEnum.HIDDEN);
		assertEquals(2, adventureTwo.getAdventureID());
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.HIDDEN);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		assertEquals(1, q.getAdventureList().get(0).getAdventureID());
		assertEquals(2, q.getAdventureList().get(1).getAdventureID());
	}
}
