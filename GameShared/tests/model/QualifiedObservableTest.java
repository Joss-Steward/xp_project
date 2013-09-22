package model;

import static org.junit.Assert.assertEquals;

import java.util.Observer;

import org.easymock.EasyMock;
import org.junit.Test;

/**
 * @author Merlin
 * 
 */
public class QualifiedObservableTest
{
	/**
	 * Just make sure that if we add an observer for a given qualifier, it gets
	 * updated when we notify
	 */
	@Test
	public void addOneGetsNotified()
	{
		QualifiedObservable obs = new MockQualifiedObservable();
		Observer observer = EasyMock.createMock(Observer.class);
		observer.update(EasyMock.eq(obs), EasyMock.isA(TestReport1.class));
		EasyMock.replay(observer);

		obs.addObserver(observer, TestReport1.class);
		assertEquals(1, obs.countObservers(TestReport1.class));
		obs.notifyObservers(new TestReport1());
		EasyMock.verify(observer);
	}

	/**
	 * Set up two observables that listen for different types of notification.
	 * Then make sure that only the appropriate one gets updated when a
	 * particular type of notification happens
	 */
	@Test
	public void addTwoDifferentOneGetsNotified()
	{
		QualifiedObservable obs = new MockQualifiedObservable();
		Observer movementObserver = EasyMock.createMock(Observer.class);
		Observer otherObserver = EasyMock.createMock(Observer.class);
		movementObserver.update(EasyMock.eq(obs),EasyMock.isA(TestReport2.class));
		EasyMock.replay(movementObserver);
		EasyMock.replay(otherObserver);

		obs.addObserver(movementObserver, TestReport2.class);
		obs.addObserver(otherObserver, TestReport1.class);
		obs.notifyObservers(new TestReport2());
		EasyMock.verify(movementObserver);
		EasyMock.verify(otherObserver);
	}

	/**
	 * Set up two observables that listen for the same types of notification.
	 * Then make sure that both get updated when a particular type of
	 * notification happens
	 */
	@Test
	public void addTwoSameBothGetNotified()
	{
		QualifiedObservable obs = new MockQualifiedObservable();
		Observer movementObserver1 = EasyMock.createMock(Observer.class);
		Observer movementObserver2 = EasyMock.createMock(Observer.class);
		movementObserver1.update(EasyMock.eq(obs), EasyMock.isA(TestReport1.class));
		movementObserver2.update(EasyMock.eq(obs), EasyMock.isA(TestReport1.class));
		EasyMock.replay(movementObserver1);
		EasyMock.replay(movementObserver2);

		obs.addObserver(movementObserver1, TestReport1.class);
		obs.addObserver(movementObserver2, TestReport1.class);
		obs.notifyObservers(new TestReport1());
		EasyMock.verify(movementObserver1);
		EasyMock.verify(movementObserver2);
	}

	/**
	 * After an observer gets removed for given type of information, it no
	 * longer gets updated for that type of information
	 */
	@Test
	public void afterDeleteNoNotification()
	{
		QualifiedObservable obs = new MockQualifiedObservable();
		Observer movementObserver1 = EasyMock.createMock(Observer.class);
		Observer movementObserver2 = EasyMock.createMock(Observer.class);
		movementObserver2.update(EasyMock.eq(obs), EasyMock.isA(TestReport1.class));
		EasyMock.replay(movementObserver1);
		EasyMock.replay(movementObserver2);

		obs.addObserver(movementObserver1, TestReport1.class);
		obs.addObserver(movementObserver2, TestReport1.class);
		obs.deleteObserver(movementObserver1, TestReport1.class);
		obs.notifyObservers(new TestReport1());
		EasyMock.verify(movementObserver1);
		EasyMock.verify(movementObserver2);
	}

	/**
	 * If an observer is listening to two types of information, saying it no
	 * longer wants to receive one type, it still receives the other
	 */
	@Test
	public void afterDeleteStillNotifiedOfOtherTypes()
	{
		QualifiedObservable obs = new MockQualifiedObservable();
		Observer observer = EasyMock.createMock(Observer.class);
		observer.update(EasyMock.eq(obs), EasyMock.isA(TestReport2.class));
		EasyMock.replay(observer);

		obs.addObserver(observer, TestReport1.class);
		obs.addObserver(observer, TestReport2.class);
		obs.deleteObserver(observer, TestReport1.class);
		obs.notifyObservers(new TestReport1());
		obs.notifyObservers(new TestReport2());
		EasyMock.verify(observer);
	}

	/**
	 * Just make sure that nothing goes wrong if you remove the last object that
	 * is interested in one type of information
	 */
	@Test
	public void canDeleteAllOfOneType()
	{
		QualifiedObservable obs = new MockQualifiedObservable();
		Observer movementObserver1 = EasyMock.createMock(Observer.class);
		Observer movementObserver2 = EasyMock.createMock(Observer.class);
		EasyMock.replay(movementObserver1);
		EasyMock.replay(movementObserver2);

		obs.addObserver(movementObserver1, TestReport1.class);
		obs.addObserver(movementObserver2, TestReport1.class);
		obs.deleteObserver(movementObserver1, TestReport1.class);
		obs.deleteObserver(movementObserver2, TestReport1.class);
		obs.notifyObservers(new TestReport1());
		EasyMock.verify(movementObserver1);
		EasyMock.verify(movementObserver2);
	}

	/**
	 * If you try to register for a type of information that the observable does
	 * not report, an exception should be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void cannotObserveAnUnsupportedQualifier()
	{
		QualifiedObservable obs = new MockQualifiedObservable();
		Observer observer1 = EasyMock.createMock(Observer.class);
		EasyMock.replay(observer1);

		obs.addObserver(observer1, TestReportNotReported.class);
	}
	
	private class TestReport1 implements QualifiedObservableReport
	{
		
	}
	
	private class TestReport2 implements QualifiedObservableReport
	{
		
	}
	
	private class TestReportNotReported implements QualifiedObservableReport
	{
		
	}

	private class MockQualifiedObservable extends QualifiedObservable
	{

		/**
		 * 
		 * @see model.QualifiedObservable#notifiesOn(java.lang.Class)
		 */
		@Override
		public boolean notifiesOn(Class<?> reportType)
		{
			if ((reportType.equals(TestReport1.class))
					|| (reportType.equals(TestReport2.class)))
			{
				return true;
			}
			return false;
		}

	}

}
