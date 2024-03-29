package communication.handlers;

import static org.junit.Assert.*;
import model.ClientModelFacade;
import model.CommandRemovePlayer;

import org.junit.Before;
import org.junit.Test;

import communication.messages.PlayerLeaveMessage;

/**
 * Make sure the message is handled properly
 * 
 * @author Merlin
 * 
 */
public class PlayerLeaveMessageHandlerTest
{

	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void reset()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
	}

	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		PlayerLeaveMessageHandler h = new PlayerLeaveMessageHandler();
		assertEquals(PlayerLeaveMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * We should add the player to the player manager
	 * 
	 * @throws InterruptedException
	 *             shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		PlayerLeaveMessage msg = new PlayerLeaveMessage(1);
		PlayerLeaveMessageHandler handler = new PlayerLeaveMessageHandler();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		CommandRemovePlayer cmd = (CommandRemovePlayer)ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(1, cmd.getPlayerID());
	}

}
