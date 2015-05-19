package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.QualifiedObservableConnector;
import model.QualifiedObserver;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import edu.ship.shipsim.client.model.CommandRemovePlayer;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.PlayerManager;
import edu.ship.shipsim.client.model.reports.PlayerDisconnectedFromAreaServerReport;

/**
 * @author nhydock
 *
 */
public class CommandRemovePlayerTest {


	/**
	 * Setup the model facade for mock testing
	 */
	@Before
	public void setup()
	{
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, true);
	}

	/**
	 * Test executing a remove player command
	 */
	@Test
	public void testExecution()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		obs.receiveReport(EasyMock.isA(PlayerDisconnectedFromAreaServerReport.class));
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				PlayerDisconnectedFromAreaServerReport.class);
		EasyMock.replay(obs);

		// setup the player
		PlayerManager pm = PlayerManager.getSingleton();
		pm.initiateLogin("john", "pw");
		try
		{
			pm.finishLogin(1);
		} catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("Could not create this client's player from login");
		}

		CommandRemovePlayer cmd = new CommandRemovePlayer(1);
		cmd.execute();

		EasyMock.verify(obs);
	}
}
