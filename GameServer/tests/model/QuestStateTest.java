package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.AdventureState;
import model.IllegalAdventureChangeException;
import model.IllegalQuestChangeException;
import model.OptionsManager;
import model.PlayerManager;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;
import model.QuestManager;
import model.QuestState;
import model.reports.QuestStateChangeReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import testData.QuestsForTest;
import data.AdventureStateEnum;
import data.QuestStateEnum;
import datasource.DatabaseException;
import datasource.DatabaseTest;

/**
 * Test for the QuestState Class
 * 
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
		OptionsManager.getSingleton().setTestMode(true);
		QuestManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}
	
	/**
	 * Test creating a very simple quest, and retreiving its information
	 * @throws IllegalQuestChangeException shouldn't
	 */
	@Test
	public void testInitialize() throws IllegalQuestChangeException
	{
		QuestState qs = new QuestState(2, 1, QuestStateEnum.AVAILABLE, true);

		assertEquals(2, qs.getPlayerID());
		assertEquals(1, qs.getID());
		assertEquals(QuestStateEnum.AVAILABLE, qs.getStateValue());
		assertTrue(qs.isNeedingNotification());
	}

	/**
	 * Test adding an ArrayList of adventures into quest
	 */
	@Test
	public void testAddAdventures()
	{
		QuestState qs = new QuestState(2, 1, QuestStateEnum.HIDDEN, true);
		ArrayList<AdventureState> adventureList = new ArrayList<AdventureState>();
		AdventureState as1 = new AdventureState(1, AdventureStateEnum.HIDDEN, false);
		AdventureState as2 = new AdventureState(2, AdventureStateEnum.HIDDEN, false);

		adventureList.add(as1);
		adventureList.add(as2);

		qs.addAdventures(adventureList);

		assertEquals(2, qs.getSizeOfAdventureList());
	}

	/**
	 * Test the change in quest's state when triggered
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testTriggerQuest() throws IllegalAdventureChangeException, IllegalQuestChangeException, DatabaseException
	{
		QuestState quest = new QuestState(2, 1, QuestStateEnum.AVAILABLE, true);
		quest.trigger();
		assertEquals(QuestStateEnum.TRIGGERED, quest.getStateValue());
		assertTrue(quest.isNeedingNotification());
	}

	/**
	 * Test to make sure you can't trigger finished quests
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 * @throws DatabaseException shouldn't
	 */
	@Test(expected=IllegalQuestChangeException.class)
	public void testTriggerFinishedQuest() throws IllegalQuestChangeException, DatabaseException, IllegalAdventureChangeException
	{
		QuestState quest = new QuestState(2, 1, QuestStateEnum.FINISHED, false);
		quest.trigger();
	
	}

	/**
	 * Test that when a quest is triggered, its adventures get triggered as well
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testTriggerQuestsAdventures() throws IllegalAdventureChangeException, IllegalQuestChangeException, DatabaseException
	{
		QuestState qs = new QuestState(2, 1, QuestStateEnum.AVAILABLE, false);
		ArrayList<AdventureState> adList = new ArrayList<AdventureState>();

		AdventureState as1 = new AdventureState(1, AdventureStateEnum.HIDDEN, false);
		AdventureState as2 = new AdventureState(2, AdventureStateEnum.HIDDEN, false);
		AdventureState as3 = new AdventureState(3, AdventureStateEnum.HIDDEN, false);

		adList.add(as1);
		adList.add(as2);
		adList.add(as3);

		qs.addAdventures(adList);
		adList = qs.getAdventureList();

		qs.trigger();

		for (AdventureState as : adList)
		{
			assertEquals(AdventureStateEnum.TRIGGERED, as.getState());
			assertFalse(as.isNeedingNotification());
		}
	}

	/**
	 * When the right number of adventures are complete (with or without
	 * notifications complete) the quest should become fulfilled and the
	 * appropriate report should be generated
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 */
	@Test
	public void testFulfilling() throws DatabaseException, IllegalQuestChangeException
	{
		
		PlayerManager.getSingleton().addPlayer(2);
		int origExperiencePoints = PlayerManager.getSingleton().getPlayerFromID(2)
				.getExperiencePoints();
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				QuestStateChangeReport.class);
		QuestStateChangeReport rpt = new QuestStateChangeReport(1,
				QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID(),
				QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestTitle(),
				QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestDescription(), QuestStateEnum.FULFILLED);
		obs.receiveReport(rpt);
		EasyMock.replay(obs);
		QuestState qs = new QuestState(2, QuestsForTest.ONE_SAME_LOCATION_QUEST.getQuestID(), QuestStateEnum.TRIGGERED, false);
		ArrayList<AdventureState> adList = new ArrayList<AdventureState>();

		PlayerManager.getSingleton().addPlayer(2);
		
		AdventureState as = new AdventureState(1, AdventureStateEnum.COMPLETED, true);
		adList.add(as);
		as = new AdventureState(2, AdventureStateEnum.COMPLETED, false);

		adList.add(as);
		as = new AdventureState(3, AdventureStateEnum.COMPLETED, false);
		adList.add(as);
		as = new AdventureState(4, AdventureStateEnum.TRIGGERED, false);
		adList.add(as);
		as = new AdventureState(5, AdventureStateEnum.COMPLETED, false);
		adList.add(as);

		qs.addAdventures(adList);
		qs.checkForFulfillmentOrFinished();
		assertEquals(
				origExperiencePoints
						+ QuestsForTest.ONE_SAME_LOCATION_QUEST.getExperienceGained(),
				PlayerManager.getSingleton().getPlayerFromID(2).getExperiencePoints());
		assertEquals(QuestStateEnum.FULFILLED, qs.getStateValue());
		assertTrue(qs.isNeedingNotification());
		EasyMock.verify(obs);
	}

	/**
	 * If a quest is already in the process of being fulfilled, no report should
	 * be generated
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 */
	@Test
	public void testFulfillingRepeatedly() throws DatabaseException, IllegalQuestChangeException
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				QuestStateChangeReport.class);
		EasyMock.replay(obs);
		QuestState qs = new QuestState(1, 3, QuestStateEnum.FULFILLED, false);
		ArrayList<AdventureState> adList = new ArrayList<AdventureState>();

		AdventureState as = new AdventureState(1, AdventureStateEnum.COMPLETED, false);
		adList.add(as);
		as = new AdventureState(2, AdventureStateEnum.COMPLETED, true);
		adList.add(as);
		as = new AdventureState(3, AdventureStateEnum.COMPLETED, false);
		adList.add(as);
		as = new AdventureState(4, AdventureStateEnum.TRIGGERED, false);
		adList.add(as);
		as = new AdventureState(5, AdventureStateEnum.COMPLETED, false);
		adList.add(as);

		qs.addAdventures(adList);
		qs.checkForFulfillmentOrFinished();
		assertEquals(QuestStateEnum.FULFILLED, qs.getStateValue());
		assertFalse(qs.isNeedingNotification());
		EasyMock.verify(obs);
	}
	
	/**
	 * Test that the new change state method works as intended.
	 * @throws IllegalQuestChangeException thrown if changing to a wrong state
	 * @throws DatabaseException shouldn't 
	 */
	@Test
	public void testChangeStateToTriggered() throws IllegalQuestChangeException, DatabaseException 
	{
		QuestState quest = new QuestState(2, 1, QuestStateEnum.HIDDEN, false);
		quest.changeState(QuestStateEnum.AVAILABLE, false);
		assertEquals(quest.getStateValue(), QuestStateEnum.AVAILABLE);
	}
	
	
	/**
     * A finished quest should be marked as finished not expired
     * @throws IllegalQuestChangeException thrown if changing to a wrong state
     * @throws DatabaseException shouldn't 
     */
    @Test
    public void testCompleteQuestNotExpired() throws IllegalQuestChangeException, DatabaseException 
    {
        QuestState quest = new QuestState(19, 8, QuestStateEnum.FINISHED, false);
        assertEquals(QuestStateEnum.FINISHED, quest.getStateValue());
    }
    
    /**
     * An available quest should be marked as expired
     * @throws IllegalQuestChangeException thrown if changing to a wrong state
     * @throws DatabaseException shouldn't 
     */
    @Test
    public void testAvailableQuestIsExpired() throws IllegalQuestChangeException, DatabaseException 
    {
        QuestState quest = new QuestState(19, 8, QuestStateEnum.AVAILABLE, false);
        assertEquals(QuestStateEnum.EXPIRED, quest.getStateValue());
    }
    
    
    /**
     * A triggered quest should be marked as expired
     * @throws IllegalQuestChangeException thrown if changing to a wrong state
     * @throws DatabaseException shouldn't 
     */
    @Test
    public void testTriggeredQuestIsExpired() throws IllegalQuestChangeException, DatabaseException 
    {
        QuestState quest = new QuestState(19, 8, QuestStateEnum.TRIGGERED, false);
        assertEquals(QuestStateEnum.EXPIRED, quest.getStateValue());
    }
}
