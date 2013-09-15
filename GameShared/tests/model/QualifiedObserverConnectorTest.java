package model;

import static org.junit.Assert.*;

import java.util.Observer;

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
		QualifiedObservableConnector.resetSingleton();
	}

	/**
	 * Test the singleton functionality. First, make sure you get the same
	 * object and then reset the singleton and make sure you get a different one
	 */
	@Test
	public void isSingleton()
	{
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		assertNotNull(connector);
		QualifiedObservableConnector connector2 = QualifiedObservableConnector.getSingleton();
		assertNotNull(connector2);
		assertSame(connector, connector2);
		QualifiedObservableConnector.resetSingleton();
		connector2 = QualifiedObservableConnector.getSingleton();
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
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		QualifiedObservable mockObservable = new MockQualifiedObservable();
		connector.registerQualifiedObservable(mockObservable, TestReport.class);
		Observer mockObserver = EasyMock.createMock(Observer.class);
		connector.registerObserver(mockObserver, TestReport.class);

		// we should expect an update on notification
		mockObserver.update(EasyMock.eq(mockObservable), EasyMock.anyObject(TestReport.class));
		EasyMock.replay(mockObserver);

		// now cause the notification
		mockObservable.notifyObservers(new TestReport());
		EasyMock.verify(mockObserver);
	}

	/**
	 * You should be able to register the observer and the observable in either
	 * order, so this test is the same as registerQualifedObservable, but the
	 * observer is registered first.
	 */
	@Test
	public void canRegisterObserverBeforeObservable()
	{
		// set up the connection
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		QualifiedObservable mockObservable = new MockQualifiedObservable();
		Observer mockObserver = EasyMock.createMock(Observer.class);
		connector.registerObserver(mockObserver, TestReport.class);
		connector.registerQualifiedObservable(mockObservable, TestReport.class);

		// we should expect an update on notification
		mockObserver.update(EasyMock.eq(mockObservable), EasyMock.anyObject(TestReport.class));
		EasyMock.replay(mockObserver);

		// now cause the notification
		mockObservable.notifyObservers(new TestReport());
		EasyMock.verify(mockObserver);
	}

	/**
	 * Make sure that if we unregister an observable for a given report type, the observers of that
	 * report type no longer get updated on notification
	 */
	@Test
	public void canUnRegisterAnObservable()
	{
		// set up the connection
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		QualifiedObservable mockObservable = new MockQualifiedObservable();
		Observer mockObserver = EasyMock.createMock(Observer.class);
		connector.registerObserver(mockObserver, TestReport.class);
		connector.registerQualifiedObservable(mockObservable, TestReport.class);

		// no notification should be expected
		EasyMock.replay(mockObserver);

		connector.unregisterQualifiedObservable(mockObservable, TestReport.class);
		mockObservable.notifyObservers(new TestReport());
		EasyMock.verify(mockObserver);
	}

	/**
	 * Make sure that if we unregister an observer, it no longer gets updated on notification
	 */
	@Test
	public void canUnRegisterAnObserver()
	{
		// set up the connection
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		QualifiedObservable mockObservable = new MockQualifiedObservable();
		Observer mockObserver = EasyMock.createMock(Observer.class);
		connector.registerObserver(mockObserver, TestReport.class);
		connector.registerQualifiedObservable(mockObservable, TestReport.class);

		// no notification should be expected
		EasyMock.replay(mockObserver);

		connector.unregisterObserver(mockObserver, TestReport.class);
		mockObservable.notifyObservers(new TestReport());
		EasyMock.verify(mockObserver);
	}

	/**
	 * If we unregister an observer, adding subsequent observables for that
	 * report type should NOT connect them
	 */
	@Test
	public void unregistrationForgetsObserver()
	{
		// set up the connection
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		QualifiedObservable mockObservable = new MockQualifiedObservable();
		Observer mockObserver = EasyMock.createMock(Observer.class);
		connector.registerObserver(mockObserver, TestReport.class);
		connector.unregisterObserver(mockObserver, TestReport.class);
		connector.registerQualifiedObservable(mockObservable, TestReport.class);

		// no notification should be expected
		EasyMock.replay(mockObserver);

		mockObservable.notifyObservers(new TestReport());
		EasyMock.verify(mockObserver);
	}

	/**
	 * We just want to be sure that, if you ask to unregister for something you aren't
	 * connected to, we just ignore you.
	 */
	@Test
	public void observerUnregistrationWhenNotRegistered()
	{
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		Observer mockObserver = EasyMock.createMock(Observer.class);
		connector.unregisterObserver(mockObserver, TestReport.class);
	}

	/**
	 * If we unregister an observable, adding subsequent observables for that
	 * report type should NOT connect them
	 */
	@Test
	public void unregistrationOfObservableForgetsObserver()
	{
		// set up the connection
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		QualifiedObservable mockObservable = new MockQualifiedObservable();
		Observer mockObserver = EasyMock.createMock(Observer.class);
		connector.registerQualifiedObservable(mockObservable, TestReport.class);
		connector.unregisterQualifiedObservable(mockObservable, TestReport.class);
		connector.registerObserver(mockObserver, TestReport.class);
		
		// no notification should be expected
		EasyMock.replay(mockObserver);

		mockObservable.notifyObservers(new TestReport());
		EasyMock.verify(mockObserver);
	}
	
	/**
	 * We just want to be sure that, if you ask to unregister for something you aren't
	 * connected to, we just ignore you.
	 */
	@Test
	public void observableUnregistrationWhenNotRegistered()
	{
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		QualifiedObservable mockObservable = new MockQualifiedObservable();
		connector.unregisterQualifiedObservable(mockObservable, TestReport.class);
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
