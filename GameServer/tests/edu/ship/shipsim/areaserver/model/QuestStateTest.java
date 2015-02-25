package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Test for the QuestState Class 
 * @author Ryan
 *
 */
public class QuestStateTest 
{

	/**
	 * Test creating a very simple quest, and retreiving its information
	 */
	@Test
	public void testInitialize() 
	{
		QuestState qs = new QuestState(1, "available");
		
		assertEquals(1, qs.getID());
		assertEquals("available", qs.getState());
	}
	
	/**
	 * Test adding an ArrayList of adventures into quest
	 */
	@Test
	public void testAddAdventures()
	{
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		AdventureState as1 = new AdventureState(1, "hidden");
		AdventureState as2 = new AdventureState(2, "hidden");
		
		adventureList.add(as1);
		adventureList.add(as2);
		
		QuestState qs = new QuestState(1, "hidden");
		qs.addAdventures(adventureList);
		
		assertEquals(2, qs.getSizeOfAdventureList());
	}
	
	/**
	 * Test the change in quest's state when triggered
	 */
	@Test
	public void testTriggerQuest()
	{
		QuestState quest = new QuestState(1, "hidden");
		quest.trigger();
		assertEquals("available", quest.getState());
	}

	/**
	 * Test to make sure you can't trigger completed quests
	 */
	@Test
	public void testTriggerCompletedQuest()
	{
		QuestState quest = new QuestState(1, "completed");
		quest.trigger();
		assertEquals("completed", quest.getState());
	}
	
	/*Need to add a test where a when a quest gets triggered, all of its 
	adventures should get triggered as well*/
	
	/**
	 * Test that when a quest is triggered, its adventures get triggered as well
	 */
	@Test
	public void testTriggerQuestsAdventures()
	{
		QuestState qs = new QuestState(1, "hidden");
		ArrayList<AdventureState> adList = new ArrayList<AdventureState>();
		
		AdventureState as1 = new AdventureState(1, "hidden");
		AdventureState as2 = new AdventureState(2, "hidden");
		AdventureState as3 = new AdventureState(3, "hidden");
		
		adList.add(as1);
		adList.add(as2);
		adList.add(as3);
		
		qs.addAdventures(adList);
		adList = qs.getAdventureList();

		qs.trigger();
		
		for(AdventureState as : adList)
		{
			assertEquals("pending", as.getState());
		}
		
	}
}
