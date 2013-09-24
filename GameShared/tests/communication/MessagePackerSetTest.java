package communication;

import static org.junit.Assert.assertNotNull;

import java.util.Observer;

import model.QualifiedObservable;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

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
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		QualifiedObservableConnector.resetSingleton();
	}
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
		MessagePacker packer = new MockMessagePacker();
		QualifiedObservableReport report = new TestReport1();
		
		MessagePackerSet set = new MessagePackerSet();
		set.registerPacker(packer);
		Message result = set.pack(report);
		assertNotNull(result);
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
		QualifiedObservableReport report = EasyMock.createMock(QualifiedObservableReport.class);
		EasyMock.replay(msg);

		set.pack(report);
		EasyMock.verify(msg);

	}

	/**
	 * When we ask it to, the packer set should run through all of the packers
	 * and hook up an observable to the type of report each one wants to pack
	 */
	@Test
	public void hooksUpObservers()
	{
		MessagePackerSet set = new MessagePackerSet();
		MessagePacker packer = new MockMessagePacker();
		
		//Set up observable and observer that are interested in the report our mock packer is packing
		QualifiedObservable obs = new MockQualifiedObservable();
		QualifiedObservableConnector.getSingleton().registerQualifiedObservable(obs, TestReport1.class);
		Observer observer = EasyMock.createMock(Observer.class);
		observer.update(EasyMock.eq(obs), EasyMock.isA(TestReport1.class));
		EasyMock.replay(observer);
		
		// register the packer, hook up the observers, then our notify should give the update our observer is expecting
		set.registerPacker(packer);
		set.hookUpObservationFor(observer);
		obs.notifyObservers(new TestReport1());

		EasyMock.verify(observer);
	}

	private class TestReport1 implements QualifiedObservableReport
	{

	}

	private class MockQualifiedObservable extends QualifiedObservable
	{

		/**
		 * @see model.QualifiedObservable#notifiesOn(java.lang.Class)
		 */
		@Override
		public boolean notifiesOn(Class<?> reportType)
		{
			if (reportType.equals(TestReport1.class))
			{
				return true;
			}
			return false;
		}

	}
	
	private class MockMessagePacker implements MessagePacker
	{

		/**
		 * @see communication.MessagePacker#pack(model.QualifiedObservableReport)
		 */
		@Override
		public Message pack(QualifiedObservableReport object)
		{
			return EasyMock.createMock(Message.class);
		}

		/**
		 * @see communication.MessagePacker#getReportWePack()
		 */
		@Override
		public Class<?> getReportWePack()
		{
			return TestReport1.class;
		}
		
	}
}
