package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Observer;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.QualifiedObservableConnector;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;
import datasource.AdventureStateList;
import datasource.QuestStateList;
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

		PlayerManager pm = PlayerManager.getSingleton();
		pm.initiateLogin("john", "pw");
		ThisClientsPlayer cp = null;
		try
		{
			cp = pm.setThisClientsPlayer(1);
		} catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("Could not create this client's player from login");
		}
		cp.addObserver(obs, PlayerMovedReport.class);
		cp.move(new Position(3, 4));

		EasyMock.verify(obs);

	}
	
	/**
	 * Make sure that you can add a quest to ThisClientsPlayer
	 */
	@Test
	public void testThisPlayerContainsClientPlayerQuest() {
		PlayerManager pm = PlayerManager.getSingleton();
		pm.initiateLogin("john", "pw");
		ThisClientsPlayer cp = null;
		
		try
		{
			cp = pm.setThisClientsPlayer(1);
		} catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("Could not create this client's player from login");
		}
		
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", AdventureStateList.HIDDEN);
		assertEquals(1, adventureOne.getAdventureID());
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", AdventureStateList.HIDDEN);
		assertEquals(2, adventureTwo.getAdventureID());
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateList.HIDDEN);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		assertEquals(2, q.getAdventureList().size());
		
		cp.addQuest(q);
		assertEquals(2, cp.getQuests().get(0).getAdventureList().get(1).getAdventureID());
		assertEquals("Test Quest 1", cp.getQuests().get(0).getQuestDescription());
	}
}
