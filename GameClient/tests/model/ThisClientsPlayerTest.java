package model;

import java.util.Observer;
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
}
