package model;

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
		QualifiedObserver observer = EasyMock.createMock(QualifiedObserver.class);
		observer.update(obs, "silly");
		EasyMock.replay(observer);

		obs.addObserver(observer, ObserverQualifier.TEST_QUALIFIER_1);
		obs.notifyObservers("silly", ObserverQualifier.TEST_QUALIFIER_1);
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
		QualifiedObserver movementObserver = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObserver otherObserver = EasyMock.createMock(QualifiedObserver.class);
		movementObserver.update(obs, "silly");
		EasyMock.replay(movementObserver);
		EasyMock.replay(otherObserver);

		obs.addObserver(movementObserver, ObserverQualifier.TEST_QUALIFIER_2);
		obs.addObserver(otherObserver, ObserverQualifier.TEST_QUALIFIER_1);
		obs.notifyObservers("silly", ObserverQualifier.TEST_QUALIFIER_2);
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
		QualifiedObserver movementObserver1 = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObserver movementObserver2 = EasyMock.createMock(QualifiedObserver.class);
		movementObserver1.update(obs, "silly");
		movementObserver2.update(obs, "silly");
		EasyMock.replay(movementObserver1);
		EasyMock.replay(movementObserver2);

		obs.addObserver(movementObserver1, ObserverQualifier.TEST_QUALIFIER_1);
		obs.addObserver(movementObserver2, ObserverQualifier.TEST_QUALIFIER_1);
		obs.notifyObservers("silly", ObserverQualifier.TEST_QUALIFIER_1);
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
		QualifiedObserver movementObserver1 = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObserver movementObserver2 = EasyMock.createMock(QualifiedObserver.class);
		movementObserver2.update(obs, "silly");
		EasyMock.replay(movementObserver1);
		EasyMock.replay(movementObserver2);

		obs.addObserver(movementObserver1, ObserverQualifier.TEST_QUALIFIER_1);
		obs.addObserver(movementObserver2, ObserverQualifier.TEST_QUALIFIER_1);
		obs.removeObserver(movementObserver1, ObserverQualifier.TEST_QUALIFIER_1);
		obs.notifyObservers("silly", ObserverQualifier.TEST_QUALIFIER_1);
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
		QualifiedObserver observer = EasyMock.createMock(QualifiedObserver.class);
		observer.update(obs, "not silly");
		EasyMock.replay(observer);

		obs.addObserver(observer, ObserverQualifier.TEST_QUALIFIER_1);
		obs.addObserver(observer, ObserverQualifier.TEST_QUALIFIER_2);
		obs.removeObserver(observer, ObserverQualifier.TEST_QUALIFIER_1);
		obs.notifyObservers("silly", ObserverQualifier.TEST_QUALIFIER_1);
		obs.notifyObservers("not silly", ObserverQualifier.TEST_QUALIFIER_2);
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
		QualifiedObserver movementObserver1 = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObserver movementObserver2 = EasyMock.createMock(QualifiedObserver.class);
		EasyMock.replay(movementObserver1);
		EasyMock.replay(movementObserver2);

		obs.addObserver(movementObserver1, ObserverQualifier.TEST_QUALIFIER_1);
		obs.addObserver(movementObserver2, ObserverQualifier.TEST_QUALIFIER_1);
		obs.removeObserver(movementObserver1, ObserverQualifier.TEST_QUALIFIER_1);
		obs.removeObserver(movementObserver2, ObserverQualifier.TEST_QUALIFIER_1);
		obs.notifyObservers("silly", ObserverQualifier.TEST_QUALIFIER_1);
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
		QualifiedObserver observer1 = EasyMock.createMock(QualifiedObserver.class);
		EasyMock.replay(observer1);

		obs.addObserver(observer1, ObserverQualifier.THIS_PLAYER_MOVEMENT_QUALIFIER);
	}

	private class MockQualifiedObservable extends QualifiedObservable
	{

		/**
		 * @see model.QualifiedObservable#notifiesOn(model.ObserverQualifier)
		 */
		@Override
		public boolean notifiesOn(ObserverQualifier qualifier)
		{
			if ((qualifier.equals(ObserverQualifier.TEST_QUALIFIER_1))
					|| (qualifier.equals(ObserverQualifier.TEST_QUALIFIER_2)))
			{
				return true;
			}
			return false;
		}

	}

}
