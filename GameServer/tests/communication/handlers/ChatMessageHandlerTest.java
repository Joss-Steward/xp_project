package communication.handlers;

import static org.junit.Assert.*;
import model.ModelFacade;
import model.QualifiedObservableConnector;

import org.junit.Before;
import org.junit.Test;

import communication.messages.ChatMessage;
import data.ChatType;
import data.Position;

/**
 * Testing the ChatMessageHandler
 * 
 * @author Josh
 */

public class ChatMessageHandlerTest 
{
	
	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void reset()
	{
		ModelFacade.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}
	
	/**
	 * Tests that getTypeWeHandle method returns correct type.
	 */
	@Test
	public void testTypeWeHandle()
	{
		ChatMessageHandler h = new ChatMessageHandler();
		assertEquals(ChatMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * Testing to see if a command is queued after receiving a message
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void handleChatMessage() throws InterruptedException
	{
		reset();
		
		ChatMessage cm = new ChatMessage("Bob", "Hey", new Position(0,0), ChatType.Local);
		ChatMessageHandler ch = new ChatMessageHandler();
		ch.process(cm);
		assertEquals(1,ModelFacade.getSingleton().queueSize());
		
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		reset();
	}
}
