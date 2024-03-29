package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;

import org.easymock.EasyMock;
import org.junit.Test;

import communication.CommunicationException;
import communication.ConnectionManager;
import communication.handlers.MessageHandlerSet;
import communication.messages.Message;
import communication.messages.StubMessage1;
import communication.messages.StubMessage2;

/**
 * Tests for the generic MessageProcessor
 * 
 * @author merlin
 * 
 */
public class MessageHandlerSetTest
{

	/**
	 * Make sure it registers all of the handlers in its package. If we can
	 * process messages of the type each stub handler expects, then they got
	 * registered
	 * 
	 * @throws CommunicationException shouldn't
	 */
	@Test
	public void detectsAndRegisters() throws CommunicationException
	{
		MessageHandlerSet proc = new MessageHandlerSet(null);

		proc.process(new StubMessage1());
		proc.process(new StubMessage2());

	}

	/**
	 * If there isn't any handler for the type of message, an exception should
	 * be thrown
	 * 
	 * @throws CommunicationException should
	 */
	@Test(expected = CommunicationException.class)
	public void noSuchHandler() throws CommunicationException
	{
		MessageHandlerSet proc = new MessageHandlerSet(null);
		Message msg = EasyMock.createMock(Message.class);
		EasyMock.replay(msg);

		proc.process(msg);

	}

	/**
	 * Make sure that when we tell the set about its accumulator, it tells each
	 * of the handlers it contains
	 */
	@Test
	public void accumulatorIsBroadcast()
	{
		ConnectionManager cm = EasyMock.createMock(ConnectionManager.class);
		MessageHandlerSet proc = new MessageHandlerSet(null);
		proc.setConnectionManager(cm);

		Collection<MessageHandler> handlers = proc.getHandlers();
		Iterator<MessageHandler> i = handlers.iterator();
		while (i.hasNext())
		{
			MessageHandler h = i.next();
			assertEquals(cm, h.getConnectionManager());
		}
	}
}
