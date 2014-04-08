package communication.handlers;

import static org.junit.Assert.*;
import model.ModelFacade;

import org.junit.Before;
import org.junit.Test;

import communication.messages.ChatMessage;
import communication.messages.ChatMessage.ChatType;
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
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
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
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
	}
}
