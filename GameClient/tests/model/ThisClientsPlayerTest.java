package model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.ClientPlayerQuestTest;
import model.ClientPlayerManager;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;
import model.ThisClientsPlayer;
import model.reports.AdventureNeedingNotificationReport;
import model.reports.ExperiencePointsChangeReport;
import model.reports.QuestNeedingNotificationReport;
import model.reports.ThisClientsPlayerMovedReport;
import model.reports.QuestStateReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import data.AdventureStateEnum;
import data.Position;
import data.QuestStateEnum;
import datasource.LevelRecord;

/**
 * Tests behaviors that are unique to the player playing on this client
 * 
 * @author merlin
 * 
 */
public class ThisClientsPlayerTest
{

	/**
	 * Reset the singletons
	 */
	@Before
	public void setup()
	{
		ClientPlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * Make sure that observers get an appropriate notification when the current
	 * player moves
	 */
	@Test
	public void notifiesOnMove()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, ThisClientsPlayerMovedReport.class);
		ThisClientsPlayerMovedReport report = new ThisClientsPlayerMovedReport(1, new Position(3, 4));
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		cp.move(new Position(3, 4));

		EasyMock.verify(obs);

	}
	
	/**
	 * Test that the client player notifies on quest request
	 */
	@Test
	public void notifiesOnQuestRequest()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		ClientPlayerQuest q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();
		cp.addQuest(q);
		ArrayList<ClientPlayerQuest> expected = new ArrayList<ClientPlayerQuest>() ;
		expected.add(q);
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, QuestStateReport.class);
		QuestStateReport report = new QuestStateReport(expected);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.sendCurrentQuestStateReport();

		EasyMock.verify(obs);

	}
	
	/**
	 * Make sure that you can add a quest to ThisClientsPlayer
	 */
	@Test
	public void testThisPlayerContainsClientPlayerQuest() {
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		
		ClientPlayerQuest q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();
		
		cp.addQuest(q);
		assertEquals(2, cp.getQuests().get(0).getAdventureList().get(1).getAdventureID());
		assertEquals("Test Quest 1", cp.getQuests().get(0).getQuestDescription());
	}

	static ThisClientsPlayer setUpThisClientsPlayerAsNumberOne()
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		PlayersForTest john = PlayersForTest.JOHN;
		pm.initiateLogin(john.getPlayerName(), john.getPlayerPassword());
		ThisClientsPlayer cp = null;
		
		try
		{
			cp = pm.finishLogin(john.getPlayerID());
		} catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("Could not create this client's player from login");
		}
		return cp;
	}

	
	
	/**
	 * Test that we can overwrite ("whomp") this client player quest list
	 */
	@Test
	public void canWhompOnQuestList()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		ClientPlayerQuest q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();
		cp.addQuest(q);
		
		ClientPlayerAdventure a = new ClientPlayerAdventure(42, "Test Adventure ow2", 3, AdventureStateEnum.HIDDEN, false);
		ClientPlayerQuest qow = new ClientPlayerQuest(41, "quest title", "Test Quest ow1", QuestStateEnum.HIDDEN, 42, 3, true);
		
		qow.addAdventure(a);
		
		ArrayList<ClientPlayerQuest> qList = new ArrayList<ClientPlayerQuest>();
		qList.add(qow);
		cp.overwriteQuestList(qList);
		
		
		assertEquals(qow, cp.getQuests().get(0));
	}
	
	/**
	 * Test that we can set the values of ThisClientPlayer's experience info,
	 * level description, and # points required for this player to level up 
	 * for this level
	 */
	@Test
	public void testAllExperienceInfo()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		
		LevelRecord rec = new LevelRecord("Felyne Explorer", 100);
		cp.setLevelInfo(rec, 10);
		
		assertEquals(10, cp.getExperiencePoints());
		assertEquals("Felyne Explorer", cp.getLevelRecord().getDescription());
		assertEquals(100, cp.getLevelRecord().getLevelUpPoints());
	}
	
	/**
	 * Test that we can send a report that contains the adventures that currently
	 * need notification
	 */
	@Test
	public void testSendAdventuresNeedingNotificationReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		
		ClientPlayerAdventure a = new ClientPlayerAdventure(1, "Test Adventure 1", 0, AdventureStateEnum.COMPLETED, true);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "questtitle", "Test Quest 1", QuestStateEnum.FINISHED, 1, 2, true);
		q.addAdventure(a);
		cp.addQuest(q);
		
		ArrayList<ClientPlayerQuest> questList = new ArrayList<ClientPlayerQuest>();
		questList.add(q);
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, AdventureNeedingNotificationReport.class);
		AdventureNeedingNotificationReport report = new AdventureNeedingNotificationReport(cp.getID(), q.getQuestID(), a.getAdventureID(), a.getAdventureDescription(), a.getAdventureState());
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.overwriteQuestList(questList);
		
		EasyMock.verify(obs);
	}
	
	/**
	 * Test that we can send a report that contains the quests that currently
	 * have need notification
	 */
	@Test
	public void testQuestNeedingNotificationReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		
		ClientPlayerAdventure a = new ClientPlayerAdventure(1, "Test Adventure 1", 0, AdventureStateEnum.COMPLETED, true);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "quest title", "Test Quest 1", QuestStateEnum.FINISHED, 1, 2, true);
		q.addAdventure(a);
		cp.addQuest(q);
		
		ArrayList<ClientPlayerQuest> questList = new ArrayList<ClientPlayerQuest>();
		questList.add(q);
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, QuestNeedingNotificationReport.class);
		QuestNeedingNotificationReport report = new QuestNeedingNotificationReport(cp.getID(), q.getQuestID(), q.getQuestDescription(), q.getQuestState());
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.overwriteQuestList(questList);
		
		EasyMock.verify(obs);
	}
	
	/**
	 * Test that we can set the values of ThisClientPlayer's experience info,
	 * level description, and # points required for this player to level up 
	 * for this level
	 */
	@Test
	public void testSendExperiencePointsChangeReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		
		int exp = 10;
		LevelRecord rec = new LevelRecord("Felyne Explorer", 10);
		cp.setLevelInfo(rec, 10);
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, ExperiencePointsChangeReport.class);
		ExperiencePointsChangeReport report = new ExperiencePointsChangeReport(exp, rec);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.sendExperiencePointsChangeReport();
		
		EasyMock.verify(obs);
	}
}
