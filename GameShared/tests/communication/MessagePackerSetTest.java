package communication;

import static org.junit.Assert.assertEquals;

import model.QualifiedObservableReport;

import org.easymock.EasyMock;
import org.junit.Test;

import communication.CommunicationException;
import communication.MessagePacker;
import communication.MessagePackerSet;
import communication.messages.Message;

/**
 * Tests for the generic MessageProcessor
 * 
 * @author merlin
 * 
 */
public class MessagePackerSetTest
{

	/**
	 * Make sure we can register a MessageHandler and use it to process messages
	 * of the registered type
	 * 
	 * @throws CommunicationException
	 *             shouldn't
	 */
	@Test
	public void justOne() throws CommunicationException
	{
		MessagePacker packer = EasyMock.createMock(MessagePacker.class);
		Message msg = EasyMock.createMock(Message.class);
		QualifiedObservableReport object = EasyMock.createMock(QualifiedObservableReport.class);
		EasyMock.expect(packer.pack( object)).andReturn(msg);
		EasyMock.replay(packer);
		EasyMock.replay(msg);

		MessagePackerSet set = new MessagePackerSet();
		set.registerPacker(object.getClass(), packer);
		Message result = set.pack( object);
		assertEquals(result, msg);
		EasyMock.verify(msg);
		EasyMock.verify(packer);

	}

	/**
	 * If there isn't any handler for the type of message, an exception should
	 * be thrown
	 * 
	 * @throws CommunicationException
	 *             should
	 */
	@Test(expected = CommunicationException.class)
	public void noSuchHandler() throws CommunicationException
	{
		MessagePackerSet set = new MessagePackerSet();
		Message msg = EasyMock.createMock(Message.class);
		QualifiedObservableReport object = EasyMock.createMock(QualifiedObservableReport.class);
		EasyMock.replay(msg);

		set.pack(object);
		EasyMock.verify(msg);

	}

}
