package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;
import model.Adventure;
import model.Quest;

import org.junit.Test;

import datasource.DatabaseException;

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
	 * @throws DatabaseException
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
	 * @throws DatabaseException 
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
	 * @throws DatabaseException
	 */
	@Test
	public void testGettingAQuestsAdventures() throws DatabaseException
	{
		QuestManager qm = QuestManager.getSingleton();
		Quest quest = qm.getQuest(1);
		
		int i = 1;
		for(Adventure a: quest.getAdventures())
		{
			assertEquals(i, a.getID());
			assertEquals("Adventure Description " + i, a.getDescription());
			i++;
		}
	}
}
