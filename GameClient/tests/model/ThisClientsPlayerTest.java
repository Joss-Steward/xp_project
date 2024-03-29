package model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerManager;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;
import model.ThisClientsPlayer;
import model.reports.AdventureNeedingNotificationReport;
import model.reports.KnowledgePointsChangeReport;
import model.reports.QuestNeedingNotificationReport;
import model.reports.ThisClientsPlayerMovedReport;
import model.reports.QuestStateReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.ClientPlayerAdventureState;
import data.ClientPlayerQuestState;
import data.ClientPlayerQuestTest;
import testData.PlayersForTest;
import datasource.LevelRecord;
import datatypes.AdventureStateEnum;
import datatypes.Position;
import datatypes.QuestStateEnum;

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
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				ThisClientsPlayerMovedReport.class);
		ThisClientsPlayerMovedReport report = new ThisClientsPlayerMovedReport(1,
				new Position(3, 4));
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);
		cp.move(new Position(3, 4));

		EasyMock.verify(obs);

	}

	/**
	 * Test that the client player notifies on quest request
	 */
	@Test
	public void notifiesOnQuestRequest()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);
		ClientPlayerQuestState q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();
		cp.addQuest(q);
		ArrayList<ClientPlayerQuestState> expected = new ArrayList<ClientPlayerQuestState>();
		expected.add(q);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				QuestStateReport.class);
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
	public void testThisPlayerContainsClientPlayerQuest()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		ClientPlayerQuestState q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();

		cp.addQuest(q);
		assertEquals(2, cp.getQuests().get(0).getAdventureList().get(1).getAdventureID());
		assertEquals("Test Quest 1", cp.getQuests().get(0).getQuestDescription());
	}

	static ThisClientsPlayer setUpThisClientsPlayer(PlayersForTest player)
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		pm.initiateLogin(player.getPlayerName(), player.getPlayerPassword());
		ThisClientsPlayer cp = null;

		try
		{
			cp = pm.finishLogin(player.getPlayerID());
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
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);
		ClientPlayerQuestState q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();
		cp.addQuest(q);

		ClientPlayerAdventureState a = new ClientPlayerAdventureState(42, "Test Adventure ow2", 3,
				AdventureStateEnum.HIDDEN, false, true, "Chair", QuestStateEnum.AVAILABLE);
		ClientPlayerQuestState qow = new ClientPlayerQuestState(41, "quest title",
				"Test Quest ow1", QuestStateEnum.AVAILABLE, 42, 3, true, null);

		qow.addAdventure(a);

		ArrayList<ClientPlayerQuestState> qList = new ArrayList<ClientPlayerQuestState>();
		qList.add(qow);
		cp.overwriteQuestList(qList);

		assertEquals(qow, cp.getQuests().get(0));
	}

	/**
	 * Test that we can set the values of ThisClientPlayer's experience info,
	 * level description, and # points required for this player to level up for
	 * this level
	 */
	@Test
	public void testAllExperienceInfo()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		LevelRecord rec = new LevelRecord("Felyne Explorer", 100, 10, 7);
		cp.setLevelInfo(rec, 10);

		assertEquals(10, cp.getExperiencePoints());
		assertEquals("Felyne Explorer", cp.getLevelRecord().getDescription());
		assertEquals(100, cp.getLevelRecord().getLevelUpPoints());
	}

	/**
	 * Test that we can send a report that contains the adventures that
	 * currently need notification
	 */
	@Test
	public void testSendAdventuresNeedingNotificationReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		ClientPlayerAdventureState a = new ClientPlayerAdventureState(1, "Test Adventure 1", 0,
				AdventureStateEnum.COMPLETED, true, true, "Mom", QuestStateEnum.AVAILABLE);
		ClientPlayerQuestState q = new ClientPlayerQuestState(1, "questtitle", "Test Quest 1",
				QuestStateEnum.COMPLETED, 1, 2, true, null);
		q.addAdventure(a);
		cp.addQuest(q);

		ArrayList<ClientPlayerQuestState> questList = new ArrayList<ClientPlayerQuestState>();
		questList.add(q);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				AdventureNeedingNotificationReport.class);
		AdventureNeedingNotificationReport report = new AdventureNeedingNotificationReport(
				cp.getID(), q.getQuestID(), a.getAdventureID(),
				a.getAdventureDescription(), a.getAdventureState(), true, "Mom");
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
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		ClientPlayerAdventureState a = new ClientPlayerAdventureState(1, "Test Adventure 1", 0,
				AdventureStateEnum.COMPLETED, true, true, "Fred", QuestStateEnum.AVAILABLE);
		ClientPlayerQuestState q = new ClientPlayerQuestState(1, "quest title", "Test Quest 1",
				QuestStateEnum.COMPLETED, 1, 2, true, null);
		q.addAdventure(a);
		cp.addQuest(q);

		ArrayList<ClientPlayerQuestState> questList = new ArrayList<ClientPlayerQuestState>();
		questList.add(q);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				QuestNeedingNotificationReport.class);
		QuestNeedingNotificationReport report = new QuestNeedingNotificationReport(
				cp.getID(), q.getQuestID(), q.getQuestDescription(), q.getQuestState());
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.overwriteQuestList(questList);

		EasyMock.verify(obs);
	}

	/**
	 * Test that we can set the values of ThisClientPlayer's experience info,
	 * level description, and # points required for this player to level up for
	 * this level
	 */
	@Test
	public void testSendExperiencePointsChangeReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		int exp = 10;
		LevelRecord rec = new LevelRecord("Felyne Explorer", 10, 10, 7);
		cp.setLevelInfo(rec, 10);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				KnowledgePointsChangeReport.class);
		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(exp);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.sendExperiencePointsChangeReport();

		EasyMock.verify(obs);
	}

	/**
	 * Test that we can set the values of ThisClientPlayer's knowledge info
	 */
	@Test
	public void testAllKnowledgeInfo()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		cp.setLevelInfoKnowledge(10);

		assertEquals(10, cp.getKnowledgePoints());
	}

	/**
	 *
	 */
	@Test
	public void testSendKnowledgePointsChangeReport()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayer(PlayersForTest.JOHN);

		int exp = 10;
		cp.setLevelInfoKnowledge(10);

		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				KnowledgePointsChangeReport.class);
		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(exp);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.sendKnowledgePointsChangeReport();
		;

		EasyMock.verify(obs);
	}

}
