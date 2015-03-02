package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import org.junit.Test;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;

/**
 * Test for the quest manager getting quests and adventures from database
 * @author lavonnediller
 *
 */
public class QuestManagerTest 
{

	/**
	 * Test initializing a quest manager
	 */
	@Test
	public void testInitialization() 
	{
		QuestManager.resetSingleton();
		QuestManager questManager = QuestManager.getSingleton();
		assertNotNull(questManager);
	}
	
	/**
	 * Make sure QuestManager is a resetable singleton
	 */
	@Test
	public void testIsSingleton()
	{
		QuestManager qm1 = QuestManager.getSingleton();
		QuestManager qm2 = QuestManager.getSingleton();
		
		assertSame(qm1, qm2);
		
		QuestManager.resetSingleton();
		assertNotSame(qm1, QuestManager.getSingleton());
	}
	
	/**
	 * Test getting a quest from the database and storing it into
	 * our Quest Manager, using a Quest Row Data Gateway Mock
	 * @throws DatabaseException throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingOneQuest() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();	
		assertEquals(1, qm.getQuest(1).getQuestID());
		assertEquals("A Big Quest", qm.getQuest(1).getDescription());
	}
	
	/**
	 * Test getting two quests from the database
	 * @throws DatabaseException throw an exception if the quest id isn't found 
	 */
	@Test
	public void testGettingTwoQuests() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		
		assertEquals(1, qm.getQuest(1).getQuestID());
		assertEquals("A Big Quest", qm.getQuest(1).getDescription());
		
		assertEquals(2, qm.getQuest(2).getQuestID());
		assertEquals("The Other Quest", qm.getQuest(2).getDescription());
	}
	
	/**
	 * Test that we can get a specific quests adventures
	 * @throws DatabaseException throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingAQuestsAdventures() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		Quest quest = qm.getQuest(1);
		
		int i = 1;
		for(Adventure a: quest.getAdventures())
		{
			if(i == 1)
			{
				assertEquals(AdventuresForTest.ONE.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.ONE.getAdventureDescription(), a.getDescription());
			}
			if(i == 2)
			{
				assertEquals(AdventuresForTest.TWO.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.TWO.getAdventureDescription(), a.getDescription());
			}
			i++;
		}
	}
	
	/**
	 * Test getting two quests and their two adventures from the db
	 * @throws DatabaseException throw an exception if the quest id isn't found
	 */
	@Test
	public void testGettingTwoQuestsAndTheirAdventures() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		Quest quest1 = qm.getQuest(1);
		Quest quest2 = qm.getQuest(2);

		int i = 1;
		for(Adventure a: quest1.getAdventures())
		{
			if(i == 1)
			{
				assertEquals(AdventuresForTest.ONE.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.ONE.getAdventureDescription(), a.getDescription());
			}
			if(i == 2)
			{
				assertEquals(AdventuresForTest.TWO.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.TWO.getAdventureDescription(), a.getDescription());
			}
			i++;
		}
		
		i = 1;
		for(Adventure a: quest2.getAdventures())
		{
			if(i == 1)
			{
				assertEquals(AdventuresForTest.THREE.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.THREE.getAdventureDescription(), a.getDescription());
			}
			if(i == 2)
			{
				assertEquals(AdventuresForTest.FOUR.getAdventureID(), a.getID());
				assertEquals(AdventuresForTest.FOUR.getAdventureDescription(), a.getDescription());
			}
			i++;
		}
	}
	
	/**
	 * A Database Exception should be thrown if a quest id does not exist
	 * @throws DatabaseException throw an exception if the quest id isn't found
	 */
	@SuppressWarnings("unused")
	@Test(expected=DatabaseException.class)
	public void testQuestDoesNotExits() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		Quest quest1 = qm.getQuest(5);
	}
}
