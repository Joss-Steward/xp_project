package communication.handlers;

import static org.junit.Assert.*;
import model.ClientModelFacade;

import org.junit.Before;
import org.junit.Test;

import communication.messages.ChatMessage;
import data.ChatType;
import data.Position;

/**
 * @author Frank Schmidt
 *
 */
public class ChatMessageHandlerTest 
{
	
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void setUp()
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
		ChatMessageHandler h = new ChatMessageHandler();
		assertEquals(ChatMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * We should add a command to the ModelFacade command queue
	 * 
	 * @throws InterruptedException
	 * 				shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		ChatMessageHandler handler = new ChatMessageHandler();
		ChatMessage chat = new ChatMessage("name", "message", new Position(1, 1), ChatType.World);
		handler.process(chat);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());while(ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
	}
}
