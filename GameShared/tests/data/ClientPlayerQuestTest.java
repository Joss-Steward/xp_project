package data;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import data.ClientPlayerAdventureState;
import data.ClientPlayerQuestState;
import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;

/**
 * Test the
 *
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
		Date expireDate = new Date();
		ClientPlayerQuestState q = new ClientPlayerQuestState(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 42, 133, true,
				expireDate);
		assertEquals(1, q.getQuestID());
		assertEquals("title", q.getQuestTitle());
		assertEquals("Test Quest 1", q.getQuestDescription());
		assertEquals(QuestStateEnum.AVAILABLE, q.getQuestState());
		assertEquals(42, q.getExperiencePointsGained());
		assertEquals(133, q.getAdventuresToFulfillment());
		assertEquals(expireDate, q.getExpireDate());
	}

	/**
	 * Create a new ClientPlayerQuest with two ClientPlayerAdventures
	 *
	 * @return the ClientPlayerQuest
	 */
	public static ClientPlayerQuestState createOneQuestWithTwoAdventures()
	{
		ClientPlayerAdventureState adventureOne = new ClientPlayerAdventureState(1, "Test Adventure 1", 4,
				AdventureStateEnum.HIDDEN, false, true, "Henry", QuestStateEnum.AVAILABLE);
		ClientPlayerAdventureState adventureTwo = new ClientPlayerAdventureState(2, "Test Adventure 2", 4,
				AdventureStateEnum.HIDDEN, false, false, null, QuestStateEnum.AVAILABLE);
		ClientPlayerQuestState q = new ClientPlayerQuestState(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 1, 2, true,
				null);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		ClientPlayerAdventureState a = q.getAdventureList().get(0);
		assertTrue(a.isRealLifeAdventure());
		assertEquals("Henry", a.getWitnessTitle());
		a = q.getAdventureList().get(1);
		assertFalse(a.isRealLifeAdventure());
		assertNull(a.getWitnessTitle());
		return q;
	}

	/**
	 * Create a new ClientPlayerQuest with two ClientPlayerAdventures
	 *
	 * @return the ClientPlayerQuest
	 */
	public static ClientPlayerQuestState createOneQuestWithTwoAdventuresNeedingNotification()
	{
		ClientPlayerAdventureState adventureOne = new ClientPlayerAdventureState(1, "Test Adventure 1", 4,
				AdventureStateEnum.COMPLETED, true, true, "CEO:", QuestStateEnum.AVAILABLE);
		ClientPlayerAdventureState adventureTwo = new ClientPlayerAdventureState(2, "Test Adventure 2", 2,
				AdventureStateEnum.COMPLETED, true, false, null, QuestStateEnum.AVAILABLE);
		ClientPlayerQuestState q = new ClientPlayerQuestState(1, "title", "Test Quest 1", QuestStateEnum.COMPLETED, 1, 2, true,
				null);
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
		ClientPlayerAdventureState adventureOne = new ClientPlayerAdventureState(1, "Test Adventure 1", 10,
				AdventureStateEnum.HIDDEN, false, false, null, QuestStateEnum.AVAILABLE);
		assertEquals(1, adventureOne.getAdventureID());
		ClientPlayerAdventureState adventureTwo = new ClientPlayerAdventureState(2, "Test Adventure 2", 7,
				AdventureStateEnum.HIDDEN, false, true, "Liz", QuestStateEnum.AVAILABLE);
		assertEquals(2, adventureTwo.getAdventureID());
		ClientPlayerQuestState q = new ClientPlayerQuestState(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 1, 2, true,
				null);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		assertEquals(1, q.getAdventureList().get(0).getAdventureID());
		assertEquals(2, q.getAdventureList().get(1).getAdventureID());
	}
}
