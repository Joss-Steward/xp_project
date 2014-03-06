package model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Observer;
import model.reports.ThisPlayerMovedReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Position;

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
		ThisPlayerMovedReport report = new ThisPlayerMovedReport(new Position(
				3, 4));
		obs.update(EasyMock.anyObject(ThisClientsPlayer.class),
				EasyMock.eq(report));
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
		cp.addObserver(obs, ThisPlayerMovedReport.class);
		cp.move(new Position(3, 4));

		EasyMock.verify(obs);

	}
}
