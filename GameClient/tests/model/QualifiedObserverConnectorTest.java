package model;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Merlin
 * 
 */
public class QualifiedObserverConnectorTest
{

	/**
	 * reset the singleton before each test
	 */
	@Before
	public void setUp()
	{
		QualifiedObserverConnector.resetSingleton();
	}

	/**
	 * Test the singleton functionality. First, make sure you get the same
	 * object and then reset the singleton and make sure you get a different one
	 */
	@Test
	public void isSingleton()
	{
		QualifiedObserverConnector connector = QualifiedObserverConnector.getSingleton();
		assertNotNull(connector);
		QualifiedObserverConnector connector2 = QualifiedObserverConnector.getSingleton();
		assertNotNull(connector2);
		assertSame(connector, connector2);
		QualifiedObserverConnector.resetSingleton();
		connector2 = QualifiedObserverConnector.getSingleton();
		assertNotSame(connector, connector2);
	}

	/**
	 * If we register an observer for a type with a registered observable, the
	 * observer should get updated on notify
	 */
	@Test
	public void registerQualifiedObservable()
	{
		// set up the connection
		QualifiedObserverConnector connector = QualifiedObserverConnector.getSingleton();
		QualifiedObservable mockObservable = new MockQualifiedObservable();
		connector.registerQualifiedObservable(mockObservable, TestReport.class);
		QualifiedObserver mockObserver = EasyMock.createMock(QualifiedObserver.class);
		connector.registerQualifiedObserver(mockObserver, TestReport.class);

		// we should expect an update on notification
		mockObserver.update(EasyMock.eq(mockObservable), EasyMock.anyObject(TestReport.class));
		EasyMock.replay(mockObserver);

		// now cause the notification
		mockObservable.notifyObservers(new TestReport());
		EasyMock.verify(mockObserver);
	}

	/**
	 * You should be able to register the observer and the observable in either order, so this test
	 * is the same as registerQualifedObservable, but the observer is registered first.
	 */
	@Test
	public void canRegisterObserverBeforeObservable()
	{
		// set up the connection
		QualifiedObserverConnector connector = QualifiedObserverConnector.getSingleton();
		QualifiedObservable mockObservable = new MockQualifiedObservable();
		QualifiedObserver mockObserver = EasyMock.createMock(QualifiedObserver.class);
		connector.registerQualifiedObserver(mockObserver, TestReport.class);
		connector.registerQualifiedObservable(mockObservable, TestReport.class);

		// we should expect an update on notification
		mockObserver.update(EasyMock.eq(mockObservable), EasyMock.anyObject(TestReport.class));
		EasyMock.replay(mockObserver);

		// now cause the notification
		mockObservable.notifyObservers(new TestReport());
		EasyMock.verify(mockObserver);
	}

	private class TestReport implements QualifiedObservableReport
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
			if (reportType.equals(TestReport.class))
			{
				return true;
			}
			return false;
		}

	}

}
