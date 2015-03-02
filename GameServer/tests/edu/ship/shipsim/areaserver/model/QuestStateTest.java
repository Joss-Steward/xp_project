package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import datasource.QuestStateList;
import edu.ship.shipsim.areaserver.datasource.AdventureStateList;

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
		QuestState qs = new QuestState(1, QuestStateList.AVAILABLE);
		
		assertEquals(1, qs.getID());
		assertEquals(QuestStateList.AVAILABLE, qs.getState());
	}
	
	/**
	 * Test adding an ArrayList of adventures into quest
	 */
	@Test
	public void testAddAdventures()
	{
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		AdventureState as1 = new AdventureState(1, AdventureStateList.HIDDEN);
		AdventureState as2 = new AdventureState(2, AdventureStateList.HIDDEN);
		
		adventureList.add(as1);
		adventureList.add(as2);
		
		QuestState qs = new QuestState(1, QuestStateList.HIDDEN);
		qs.addAdventures(adventureList);
		
		assertEquals(2, qs.getSizeOfAdventureList());
	}
	
	/**
	 * Test the change in quest's state when triggered
	 */
	@Test
	public void testTriggerQuest()
	{
		QuestState quest = new QuestState(1, QuestStateList.HIDDEN);
		quest.trigger();
		assertEquals(QuestStateList.AVAILABLE, quest.getState());
	}

	/**
	 * Test to make sure you can't trigger finished quests
	 */
	@Test
	public void testTriggerFinishedQuest()
	{
		QuestState quest = new QuestState(1, QuestStateList.FINISHED);
		quest.trigger();
		assertEquals(QuestStateList.FINISHED, quest.getState());
	}
	
	/**
	 * Test that when a quest is triggered, its adventures get triggered as well
	 */
	@Test
	public void testTriggerQuestsAdventures()
	{
		QuestState qs = new QuestState(1, QuestStateList.HIDDEN);
		ArrayList<AdventureState> adList = new ArrayList<AdventureState>();
		
		AdventureState as1 = new AdventureState(1, AdventureStateList.HIDDEN);
		AdventureState as2 = new AdventureState(2, AdventureStateList.HIDDEN);
		AdventureState as3 = new AdventureState(3, AdventureStateList.HIDDEN);
		
		adList.add(as1);
		adList.add(as2);
		adList.add(as3);
		
		qs.addAdventures(adList);
		adList = qs.getAdventureList();

		qs.trigger();
		
		for(AdventureState as : adList)
		{
			assertEquals(AdventureStateList.PENDING, as.getState());
		}
		
	}
}
