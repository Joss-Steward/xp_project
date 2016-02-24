package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ClientModelFacade;
import model.ClientPlayerManager;
import model.MapManager;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;

import communication.messages.OtherPlayerMovedMessage;

import data.Position;

/**
 * @author Andrew
 * 
 *         Test to see if MovementMessagHandler properly works.
 */
public class OtherPlayerMovedMessageHandlerTest
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
		assertEquals(OtherPlayerMovedMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * Tests to see if the command is built correctly, and added to the Facade.
	 * 
	 * @throws InterruptedException
	 *             shouldn't
	 * @throws NotBoundException
	 *             shouldn't
	 * @throws AlreadyBoundException
	 *             shouldn't
	 */
	@Test
	public void engineNotified() throws InterruptedException, AlreadyBoundException,
			NotBoundException
	{
		MapManager.getSingleton().changeToNewFile("testmaps/simple.tmx");
		ClientPlayerManager.getSingleton().initiateLogin(
				PlayersForTest.MATT.getPlayerName(),
				PlayersForTest.MATT.getPlayerPassword());
		ClientPlayerManager.getSingleton().finishLogin(PlayersForTest.MATT.getPlayerID());
		ClientPlayerManager.getSingleton().initializePlayer(
				PlayersForTest.MERLIN.getPlayerID(),
				PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getAppearanceType(),
				PlayersForTest.MERLIN.getPosition(),
				PlayersForTest.MERLIN.getCrew());
		Position p = new Position(1, 1);
		OtherPlayerMovedMessage msg = new OtherPlayerMovedMessage(
				PlayersForTest.MATT.getPlayerID(), p);
		OtherPlayerMovedMessageHandler handler = new OtherPlayerMovedMessageHandler();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		while (ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		assertEquals(p, ClientPlayerManager.getSingleton().getThisClientsPlayer()
				.getPosition());
	}

}
