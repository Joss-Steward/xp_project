package model;
import static org.junit.Assert.*;

import java.util.Observer;

import model.reports.LoginInitiatedReport;
import model.reports.ThisPlayerMovedReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;

/**
 * Tests the player 
 * @author merlin
 *
 */
public class PlayerTest
{

	/**
	 * Always start with a new singleton
	 */
	@Before
	public void reset()
	{
		ThisClientsPlayer.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}
	/**
	 * There should be only one player
	 */
	@Test
	public void testSingleton()
	{
		ThisClientsPlayer player1 = ThisClientsPlayer.getSingleton();
		assertSame(player1,ThisClientsPlayer.getSingleton());
		ThisClientsPlayer.resetSingleton();
		assertNotSame(player1,ThisClientsPlayer.getSingleton());
	}

	/**
	 * Make sure that observers get an appropriate notification when the current player moves
	 */
	@Test
	public void notifiesOnMove()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		ThisPlayerMovedReport report = new ThisPlayerMovedReport(new Position(3,4));
		obs.update(EasyMock.eq(ThisClientsPlayer.getSingleton()), EasyMock.eq(report));
		EasyMock.replay(obs);
		
		ThisClientsPlayer.getSingleton().addObserver(obs, ThisPlayerMovedReport.class);
		ThisClientsPlayer.getSingleton().move(new Position(3,4));
		EasyMock.verify(obs);
	}
	
	/**
	 * Just make sure he remembers when a login is started
	 */
	@Test
	public void canStartToLogin()
	{
		ThisClientsPlayer p = ThisClientsPlayer.getSingleton();
		assertFalse(p.isLoginInProgress());
		p.initiateLogin("Fred", "mommy");
		assertTrue(p.isLoginInProgress());
		assertEquals("Fred",p.getName());
	}
	
	/**
	 * Make sure that observers who want to be told when a login is initiated are told
	 */
	@Test
	public void notifiesOnLoginInitiation()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		LoginInitiatedReport report = new LoginInitiatedReport("Fred","daddy");
		QualifiedObservableConnector.getSingleton().registerObserver(obs, LoginInitiatedReport.class);
		obs.update(EasyMock.eq(ThisClientsPlayer.getSingleton()), EasyMock.eq(report));
		EasyMock.replay(obs);
		
		ThisClientsPlayer.getSingleton().initiateLogin("Fred","daddy");
		EasyMock.verify(obs);
		
	}
}
