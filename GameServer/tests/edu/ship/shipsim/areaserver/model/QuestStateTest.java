package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import datasource.AdventureStateEnum;
import datasource.QuestStateEnum;

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
		QuestState qs = new QuestState(1, QuestStateEnum.AVAILABLE);
		
		assertEquals(1, qs.getID());
		assertEquals(QuestStateEnum.AVAILABLE, qs.getStateValue());
	}
	
	/**
	 * Test adding an ArrayList of adventures into quest
	 */
	@Test
	public void testAddAdventures()
	{
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		AdventureState as1 = new AdventureState(1, AdventureStateEnum.HIDDEN);
		AdventureState as2 = new AdventureState(2, AdventureStateEnum.HIDDEN);
		
		adventureList.add(as1);
		adventureList.add(as2);
		
		QuestState qs = new QuestState(1, QuestStateEnum.HIDDEN);
		qs.addAdventures(adventureList);
		
		assertEquals(2, qs.getSizeOfAdventureList());
	}
	
	/**
	 * Test the change in quest's state when triggered
	 */
	@Test
	public void testTriggerQuest()
	{
		QuestState quest = new QuestState(1, QuestStateEnum.HIDDEN);
		quest.trigger();
		assertEquals(QuestStateEnum.AVAILABLE, quest.getStateValue());
	}

	/**
	 * Test to make sure you can't trigger finished quests
	 */
	@Test
	public void testTriggerFinishedQuest()
	{
		QuestState quest = new QuestState(1, QuestStateEnum.FINISHED);
		quest.trigger();
		assertEquals(QuestStateEnum.FINISHED, quest.getStateValue());
	}
	
	/**
	 * Test that when a quest is triggered, its adventures get triggered as well
	 */
	@Test
	public void testTriggerQuestsAdventures()
	{
		QuestState qs = new QuestState(1, QuestStateEnum.HIDDEN);
		ArrayList<AdventureState> adList = new ArrayList<AdventureState>();
		
		AdventureState as1 = new AdventureState(1, AdventureStateEnum.HIDDEN);
		AdventureState as2 = new AdventureState(2, AdventureStateEnum.HIDDEN);
		AdventureState as3 = new AdventureState(3, AdventureStateEnum.HIDDEN);
		
		adList.add(as1);
		adList.add(as2);
		adList.add(as3);
		
		qs.addAdventures(adList);
		adList = qs.getAdventureList();

		qs.trigger();
		
		for(AdventureState as : adList)
		{
			assertEquals(AdventureStateEnum.PENDING, as.getState());
		}
		
	}
}
