package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.OptionsManager;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datasource.AdventureStateEnum;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.QuestStateEnum;
import edu.ship.shipsim.areaserver.model.reports.QuestNeedsFulfillmentNotificationReport;

/**
 * Test for the QuestState Class 
 * @author Ryan
 *
 */
public class QuestStateTest extends DatabaseTest
{
	

	/**
	 * 
	 */
	@Before
	public void setUp()
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton(true);
		QuestManager.resetSingleton();
	}
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
		QuestState qs = new QuestState(1, QuestStateEnum.HIDDEN);
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		AdventureState as1 = new AdventureState(1, AdventureStateEnum.HIDDEN);
		AdventureState as2 = new AdventureState(2, AdventureStateEnum.HIDDEN);
		
		adventureList.add(as1);
		adventureList.add(as2);
		
		
		qs.addAdventures(adventureList);
		
		assertEquals(2, qs.getSizeOfAdventureList());
	}
	
	/**
	 * Test the change in quest's state when triggered
	 */
	@Test
	public void testTriggerQuest()
	{
		QuestState quest = new QuestState(1, QuestStateEnum.AVAILABLE);
		quest.trigger();
		assertEquals(QuestStateEnum.TRIGGERED, quest.getStateValue());
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
		QuestState qs = new QuestState(1, QuestStateEnum.AVAILABLE);
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
	
	/**
	 * When the right number of adventures are complete (with or without notifications complete)
	 * the quest should become fulfilled and the appropriate report should be generated
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testFulfilling() throws DatabaseException
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, QuestNeedsFulfillmentNotificationReport.class);
		QuestNeedsFulfillmentNotificationReport rpt = new QuestNeedsFulfillmentNotificationReport(13,3);
		obs.receiveReport(rpt);
		EasyMock.replay(obs);
		QuestState qs = new QuestState(3, QuestStateEnum.TRIGGERED);
		ArrayList<AdventureState> adList = new ArrayList<AdventureState>();
		
		AdventureState as = new AdventureState(1, AdventureStateEnum.COMPLETED);
		adList.add(as);
		as = new AdventureState(2, AdventureStateEnum.NEED_NOTIFICATION);
		adList.add(as);
		as = new AdventureState(3, AdventureStateEnum.COMPLETED);
		adList.add(as);
		as = new AdventureState(4, AdventureStateEnum.PENDING);
		adList.add(as);
		as = new AdventureState(5, AdventureStateEnum.COMPLETED);
		adList.add(as);
		
		qs.addAdventures(adList);
		qs.checkForFulfillment();
		assertEquals(QuestStateEnum.NEED_FULFILLED_NOTIFICATION, qs.getStateValue());
		EasyMock.verify(obs);
	}
	/**
	 * If a quest is already in the process of being fulfilled, no report should be generated
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testFulfillingRepeatedly() throws DatabaseException
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, QuestNeedsFulfillmentNotificationReport.class);
		EasyMock.replay(obs);
		QuestState qs = new QuestState(3, QuestStateEnum.NEED_FULFILLED_NOTIFICATION);
		ArrayList<AdventureState> adList = new ArrayList<AdventureState>();
		
		AdventureState as = new AdventureState(1, AdventureStateEnum.COMPLETED);
		adList.add(as);
		as = new AdventureState(2, AdventureStateEnum.NEED_NOTIFICATION);
		adList.add(as);
		as = new AdventureState(3, AdventureStateEnum.COMPLETED);
		adList.add(as);
		as = new AdventureState(4, AdventureStateEnum.PENDING);
		adList.add(as);
		as = new AdventureState(5, AdventureStateEnum.COMPLETED);
		adList.add(as);
		
		qs.addAdventures(adList);
		qs.checkForFulfillment();
		assertEquals(QuestStateEnum.NEED_FULFILLED_NOTIFICATION, qs.getStateValue());
		EasyMock.verify(obs);
	}
}
