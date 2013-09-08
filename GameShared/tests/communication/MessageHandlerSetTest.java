package communication;

import org.easymock.EasyMock;
import org.junit.Test;

import communication.CommunicationException;
import communication.MessageHandler;
import communication.MessageHandlerSet;
import communication.messages.Message;

/**
 * Tests for the generic MessageProcessor
 * @author merlin
 *
 */
public class MessageHandlerSetTest
{

	/**
	 * Make sure we can register a MessageHandler and use it to process messages of the registered type
	 * @throws CommunicationException shouldn't
	 */
	@Test
	public void justOne() throws CommunicationException
	{
		MessageHandlerSet proc = new MessageHandlerSet();
		MessageHandler handler = EasyMock.createMock(MessageHandler.class);
		Message msg = EasyMock.createMock(Message.class);
		
		handler.process(msg);
		EasyMock.replay(handler);
		EasyMock.replay(msg);
		
		proc.registerHandler(msg.getClass(), handler);
		proc.process(msg);
		EasyMock.verify(handler);
		EasyMock.verify(msg);
	}
	
	/**
	 * If there isn't any handler for the type of message, an exception should be thrown
	 * @throws CommunicationException should
	 */
	@Test(expected = CommunicationException.class)
	public void noSuchHandler() throws CommunicationException
	{
		MessageHandlerSet proc = new MessageHandlerSet();
		Message msg = EasyMock.createMock(Message.class);
		EasyMock.replay(msg);
		
		proc.process(msg);
		
	}


}
