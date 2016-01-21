package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.MapManager;
import model.ModelFacade;
import model.PlayerManager;

import org.junit.Before;
import org.junit.Test;

import communication.messages.MovementMessage;
import data.Position;
import datasource.PlayersForTest;

/**
 * @author Andrew
 * 
 *         Test to see if MovementMessagHandler properly works.
 */
public class MovementMessageHandlerTest
{
	/**
	 * reset the singletons and tell the model we are running headless
	 */
	@Before
	public void setUp()
	{
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, true);
	}
	
	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		MovementMessageHandler h = new MovementMessageHandler();
		assertEquals(MovementMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * Tests to see if the command is built correctly, and added to the Facade.
	 * 
	 * @throws InterruptedException
	 *             shouldn't
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void engineNotified() throws InterruptedException, AlreadyBoundException, NotBoundException
	{
		MapManager.getSingleton().changeToNewFile("testmaps/simple.tmx");
		PlayerManager.getSingleton().initiateLogin(PlayersForTest.MATT.getPlayerName(), PlayersForTest.MATT.getPlayerPassword());
		PlayerManager.getSingleton().finishLogin(PlayersForTest.MATT.getPlayerID());
		Position p = new Position(1, 1);
		MovementMessage msg = new MovementMessage(PlayersForTest.MATT.getPlayerID(), p);
		MovementMessageHandler handler = new MovementMessageHandler();
		handler.process(msg);
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
		while(ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		assertEquals(p,PlayerManager.getSingleton().getThisClientsPlayer().getPosition());
	}

}
