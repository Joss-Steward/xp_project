package communication.handlers;

import org.easymock.EasyMock;
import org.junit.Test;

import communication.CommunicationException;
import communication.handlers.MessageHandlerSet;
import communication.messages.Message;
import communication.messages.StubMessage1;
import communication.messages.StubMessage2;

/**
 * Tests for the generic MessageProcessor
 * @author merlin
 *
 */
public class MessageHandlerSetTest
{

	/**
	 * Make sure it registers all of the handlers in its package.
	 * If we can process messages of the type each stub handler expects, then they got registered
	 * @throws CommunicationException shouldn't
	 */
	@Test
	public void detectsAndRegisters() throws CommunicationException
	{
		MessageHandlerSet proc = new MessageHandlerSet();

		proc.process(new StubMessage1());
		proc.process(new StubMessage2());
		
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
