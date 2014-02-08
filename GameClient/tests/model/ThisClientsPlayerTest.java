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
 * Tests behaviors that are unique to the player playing on this client
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
		ThisPlayerMovedReport report = new ThisPlayerMovedReport(new Position(
				3, 4));
		obs.update(EasyMock.eq(PlayerManager.getSingleton()
				.getThisClientsPlayer()), EasyMock.eq(report));
		EasyMock.replay(obs);

		PlayerManager.getSingleton().getThisClientsPlayer()
				.addObserver(obs, ThisPlayerMovedReport.class);
		PlayerManager.getSingleton().getThisClientsPlayer()
				.move(new Position(3, 4));
		EasyMock.verify(obs);
	}

	/**
	 * Just make sure he remembers when a login is started
	 */
	@Test
	public void canStartToLogin()
	{
		ThisClientsPlayer p = PlayerManager.getSingleton()
				.getThisClientsPlayer();
		assertFalse(p.isLoginInProgress());
		p.initiateLogin("Fred", "mommy");
		assertTrue(p.isLoginInProgress());
		assertEquals("Fred", p.getName());
	}

	/**
	 * Make sure that observers who want to be told when a login is initiated
	 * are told
	 */
	@Test
	public void notifiesOnLoginInitiation()
	{
		Observer obs = EasyMock.createMock(Observer.class);
		LoginInitiatedReport report = new LoginInitiatedReport("Fred", "daddy");
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				LoginInitiatedReport.class);
		obs.update(EasyMock.eq(PlayerManager.getSingleton()
				.getThisClientsPlayer()), EasyMock.eq(report));
		EasyMock.replay(obs);

		PlayerManager.getSingleton().getThisClientsPlayer()
				.initiateLogin("Fred", "daddy");
		EasyMock.verify(obs);

	}
}
