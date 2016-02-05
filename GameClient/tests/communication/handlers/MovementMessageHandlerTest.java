package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.MapManager;
import model.ClientModelFacade;
import model.ClientPlayerManager;

import org.junit.Before;
import org.junit.Test;

import communication.messages.PlayerMovedMessage;
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
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, true);
	}
	
	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		OtherPlayerMovedMessageHandler h = new OtherPlayerMovedMessageHandler();
		assertEquals(PlayerMovedMessage.class, h.getMessageTypeWeHandle());
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
		ClientPlayerManager.getSingleton().initiateLogin(PlayersForTest.MATT.getPlayerName(), PlayersForTest.MATT.getPlayerPassword());
		ClientPlayerManager.getSingleton().finishLogin(PlayersForTest.MATT.getPlayerID());
		Position p = new Position(1, 1);
		PlayerMovedMessage msg = new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(), p);
		OtherPlayerMovedMessageHandler handler = new OtherPlayerMovedMessageHandler();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		while(ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		assertEquals(p,ClientPlayerManager.getSingleton().getThisClientsPlayer().getPosition());
	}

}
