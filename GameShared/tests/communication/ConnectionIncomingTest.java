package communication;
import java.io.IOException;


import org.easymock.EasyMock;
import org.junit.Test;

import communication.ConnectionIncoming;
import communication.MessageHandler;
import communication.MessageHandlerSet;
import communication.messages.Message;


/**
 * Test the parts of the incoming connection that can be tested without a true socket connection
 * @author merlin
 *
 */
public class ConnectionIncomingTest
{
	
	private StateAccumulator createStateAccumulator()
	{
		StateAccumulatorConnector connector = EasyMock.createMock(StateAccumulatorConnector.class);
		EasyMock.expect(connector.getMessagePackerSet(EasyMock.anyObject(StateAccumulator.class))).andReturn(new MessagePackerSet());
		EasyMock.replay(connector);
		return new StateAccumulator(connector);
	}

	/**
	 * An incoming message should be routed by the MessageProcessor to the MessageHandler register 
	 * for that type of message
	 * @throws IOException shouldn't
	 */
	@Test
	public void incomingMsgGetsProcessed() throws IOException
	{
		Message msg = EasyMock.createMock(Message.class);
		MessageHandler handler = EasyMock.createMock(MessageHandler.class);
		StateAccumulator accumulator = createStateAccumulator();
		handler.process(msg,accumulator);
		EasyMock.replay(msg);
		EasyMock.replay(handler);
		
		MessageHandlerSet messageProcessor = new MessageHandlerSet();
		messageProcessor.registerHandler(msg.getClass(), handler);
		ConnectionIncoming connection = new ConnectionIncoming(null, messageProcessor, accumulator);
		connection.processRequest(msg);
		
		EasyMock.verify(msg);
		EasyMock.verify(handler);
	}
}
