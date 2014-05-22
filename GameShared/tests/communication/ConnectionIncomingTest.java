package communication;

import java.io.IOException;

import org.junit.Test;

import communication.handlers.MessageHandlerSet;
import communication.messages.Message;
import communication.messages.StubMessage1;

/**
 * Test the parts of the incoming connection that can be tested without a true
 * socket connection
 * 
 * @author merlin
 * 
 */
public class ConnectionIncomingTest
{

	/**
	 * An incoming message should be routed by the MessageProcessor to the
	 * MessageHandler register for that type of message
	 * 
	 * @throws IOException
	 *             shouldn't
	 */
	@Test
	public void incomingMsgGetsProcessed() throws IOException
	{
		Message msg = new StubMessage1();

		MessageHandlerSet messageHandlerSet = new MessageHandlerSet(null);
		ConnectionIncoming connection = new ConnectionIncoming(null, messageHandlerSet);
		connection.processRequest(msg);

	}
}
