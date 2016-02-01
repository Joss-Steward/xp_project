package model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ClientPlayer;
import model.ClientPlayerManager;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;
import model.reports.LoginInitiatedReport;
import model.reports.PlayerConnectedToAreaServerReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;

/**
 * Tests the player manager to make sure it maintains the list of players
 * correctly
 * 
 * @author merlin
 * 
 */
public class PlayerManagerTest
{

	/**
	 * Always start with a new singleton
	 */
	@Before
	public void reset()
	{
		ClientPlayerManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * There should be only one player
	 */
	@Test
	public void testSingleton()
	{
		ClientPlayerManager player1 = ClientPlayerManager.getSingleton();
		assertSame(player1, ClientPlayerManager.getSingleton());
		ClientPlayerManager.resetSingleton();
		assertNotSame(player1, ClientPlayerManager.getSingleton());
	}

	/**
	 * Make sure we can add players and retrieve them by their player names
	 */
	@Test
	public void canAddAndRetrievePlayers()
	{
		Position pos = new Position(1, 2);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		ClientPlayer p1 = new ClientPlayer(1);
		ClientPlayer p2 = new ClientPlayer(2);
		ClientPlayer p3 = new ClientPlayer(3);
		pm.initializePlayer(1, "Player 1", "Player 1 Type", pos);
		assertEquals(p1, pm.getPlayerFromID(1));
		pm.initializePlayer(2, "Player 2", "Player 2 Type", pos);
		assertEquals(p1, pm.getPlayerFromID(1));
		assertEquals(p2, pm.getPlayerFromID(2));
		pm.initializePlayer(3, "Player 3", "Player 3 Type", pos);
		assertEquals(p1, pm.getPlayerFromID(1));
		assertEquals(p2, pm.getPlayerFromID(2));
		assertEquals(p3, pm.getPlayerFromID(3));
	}

	/**
	 * Just make sure he remembers when a login is started
	 */
	@Test
	public void canStartToLogin()
	{
		ClientPlayerManager p = ClientPlayerManager.getSingleton();
		assertFalse(p.isLoginInProgress());
		p.initiateLogin("Fred", "mommy");
		assertTrue(p.isLoginInProgress());
	}

	/**
	 * Make sure that observers who want to be told when a login is initiated
	 * are told
	 */
	@Test
	public void notifiesOnLoginInitiation()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		LoginInitiatedReport report = new LoginInitiatedReport("Fred", "daddy");
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				LoginInitiatedReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		ClientPlayerManager.getSingleton().initiateLogin("Fred", "daddy");

		EasyMock.verify(obs);
	}

	/**
	 * Test all the conditions behind setting this client's player to ensure the
	 * method is secure
	 */
	@Test
	public void testSettingThisClientsPlayer()
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();

		// test setting player without having tried logging in
		try
		{
			pm.finishLogin(1);
		} catch (AlreadyBoundException | NotBoundException e)
		{
			assertTrue(e instanceof NotBoundException);
		}

		// test setting the player while trying to log in
		try
		{
			pm.initiateLogin("bilbo", "baggins");
			pm.finishLogin(1);
		} catch (AlreadyBoundException | NotBoundException e)
		{
			fail("Login should have been processed, and setting should work");
		}

		// player shouldn't be able to be set after having logged in without
		// first logging out
		try
		{
			pm.finishLogin(2);
			fail("Login should have already occured and it should not allow a new player to be set");
		} catch (AlreadyBoundException | NotBoundException e)
		{
			assertTrue(e instanceof AlreadyBoundException);
		}
	}

	/**
	 * Initialize player should send a PlayerConnectedToAreaServerReport
	 */
	@Test
	public void testInitializePlayerFiresReport()
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		Position pos = new Position(1, 2);
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		PlayerConnectedToAreaServerReport report = new PlayerConnectedToAreaServerReport(
				1, "Player 1", "Player 1 Type", pos, false);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, PlayerConnectedToAreaServerReport.class);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		pm.initializePlayer(1, "Player 1", "Player 1 Type", pos);

		EasyMock.verify(obs);
	}
}