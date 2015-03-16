package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Observer;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
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
	 * Make sure that you can add a quest to ThisClientsPlayer
	 */
	@Test
	public void testThisPlayerContainsClientPlayerQuest() {
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		
		ClientPlayerQuest q = createOneQuestWithTwoAdventures();
		
		cp.addQuest(q);
		assertEquals(2, cp.getQuests().get(0).getAdventureList().get(1).getAdventureID());
		assertEquals("Test Quest 1", cp.getQuests().get(0).getQuestDescription());
	}

	private ThisClientsPlayer setUpThisClientsPlayerAsNumberOne()
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

	private ClientPlayerQuest createOneQuestWithTwoAdventures()
	{
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", AdventureStateEnum.HIDDEN);
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", AdventureStateEnum.HIDDEN);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.HIDDEN);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		return q;
	}
	
	@Test
	public void canWhompOnQuestList()
	{
		ThisClientsPlayer cp = setUpThisClientsPlayerAsNumberOne();
		ClientPlayerQuest q = createOneQuestWithTwoAdventures();
		cp.addQuest(q);
		
		ClientPlayerAdventure a = new ClientPlayerAdventure(42, "Test Adventure ow2", AdventureStateEnum.HIDDEN);
		ClientPlayerQuest qow = new ClientPlayerQuest(41, "Test Quest ow1", QuestStateEnum.HIDDEN);
		ArrayList<ClientPlayerQuest> qList = new ArrayList<ClientPlayerQuest>();
		qList.add(qow);
		cp.overwriteQuestList(qList);
		
		assertEquals(1,cp.getQuests().size());
		assertEquals(qow, cp.getQuests().get(0));
	}
}
