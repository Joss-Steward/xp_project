package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Observer;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.ClientPlayerQuestTest;
import model.QualifiedObservableConnector;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;
import datasource.AdventureStateEnum;
import datasource.PlayersForTest;
import datasource.QuestStateEnum;
import edu.ship.shipsim.client.model.PlayerManager;
import edu.ship.shipsim.client.model.ThisClientsPlayer;
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
		Observer obs = EasyMock.createMock(Observer.class);
		PlayerMovedReport report = new PlayerMovedReport(1, new Position(3, 4));
		obs.update(EasyMock.anyObject(ThisClientsPlayer.class), EasyMock.eq(report));
		EasyMock.replay(obs);

		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		cp.addObserver(obs, PlayerMovedReport.class);
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
		
		Observer obs = EasyMock.createMock(Observer.class);
		QuestStateReport report = new QuestStateReport(expected);
		obs.update(EasyMock.anyObject(ThisClientsPlayer.class), EasyMock.eq(report));
		EasyMock.replay(obs);

		cp.addObserver(obs, QuestStateReport.class);
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
		
		ClientPlayerAdventure a = new ClientPlayerAdventure(42, "Test Adventure ow2", AdventureStateEnum.HIDDEN);
		ClientPlayerQuest qow = new ClientPlayerQuest(41, "Test Quest ow1", QuestStateEnum.HIDDEN);
		
		qow.addAdventure(a);
		
		ArrayList<ClientPlayerQuest> qList = new ArrayList<ClientPlayerQuest>();
		qList.add(qow);
		cp.overwriteQuestList(qList);
		
		assertEquals(1,cp.getQuests().size());
		assertEquals(qow, cp.getQuests().get(0));
	}
}
