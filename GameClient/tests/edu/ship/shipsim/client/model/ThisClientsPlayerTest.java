package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.ClientPlayerQuestTest;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;
import datasource.AdventureStateEnum;
import datasource.LevelRecord;
import datasource.PlayersForTest;
import datasource.QuestStateEnum;
import edu.ship.shipsim.client.model.PlayerManager;
import edu.ship.shipsim.client.model.ThisClientsPlayer;
import edu.ship.shipsim.client.model.reports.AdventuresNeedingNotificationReport;
import edu.ship.shipsim.client.model.reports.ExperiencePointsChangeReport;
import edu.ship.shipsim.client.model.reports.PlayerMovedReport;
import edu.ship.shipsim.client.model.reports.QuestStateReport;

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
		PlayerManager.resetSingleton();
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
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerMovedReport.class);
		PlayerMovedReport report = new PlayerMovedReport(1, new Position(3, 4));
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
		PlayerManager pm = PlayerManager.getSingleton();
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
		
		ClientPlayerAdventure a = new ClientPlayerAdventure(42, "Test Adventure ow2", AdventureStateEnum.HIDDEN, false);
		ClientPlayerQuest qow = new ClientPlayerQuest(41, "Test Quest ow1", QuestStateEnum.HIDDEN, 42, 3);
		
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
	 * have the state of NEED_NOTIFICATION
	 */
	@Test
	public void testSendAdventuresNeedingNotificationReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		ClientPlayerQuest q = ClientPlayerQuestTest.createOneQuestWithTwoAdventuresNeedingNotification();
		cp.addQuest(q);
		ArrayList<String> expected = new ArrayList<String>() ;
		
		// Retrieve each adventure's descriptions from the quest
		for(ClientPlayerAdventure a : q.getAdventureList())
		{
			expected.add(a.getAdventureDescription());
		}
		
		ArrayList<ClientPlayerQuest> questList = new ArrayList<ClientPlayerQuest>();
		questList.add(q);
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, AdventuresNeedingNotificationReport.class);
		AdventuresNeedingNotificationReport report = new AdventuresNeedingNotificationReport(expected);
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
