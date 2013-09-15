package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

/**
 * @author Merlin
 * 
 */
public class QualifiedObserverConnector
{

	private static QualifiedObserverConnector singleton;

	private HashMap<Class<?>, ArrayList<QualifiedObservable>> observables;
	private HashMap<Class<?>, ArrayList<Observer>> observers;

	private QualifiedObserverConnector()
	{
		observables = new HashMap<Class<?>, ArrayList<QualifiedObservable>>();
		observers = new HashMap<Class<?>, ArrayList<Observer>>();
	}

	/**
	 * @return the only one of these in the syste
	 */
	public synchronized static QualifiedObserverConnector getSingleton()
	{
		if (singleton == null)
		{
			singleton = new QualifiedObserverConnector();
		}
		return singleton;
	}

	/**
	 * 
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}

	/**
	 * This is used by an observable to tell the world that he will report a
	 * given report type
	 * 
	 * @param observable
	 *            the object who wants to be observed
	 * @param reportType
	 *            the type of message it will report
	 */
	public void registerQualifiedObservable(QualifiedObservable observable, Class<?> reportType)
	{
		rememberObservable(observable, reportType);

		ArrayList<Observer> relevantObservers = observers.get(reportType);
		if (relevantObservers != null)
		{
			for (Observer observer : relevantObservers)
			{
				observable.addObserver(observer, reportType);
			}
		}
	}

	/**
	 * @param observable
	 *            the observable we should remember
	 * @param reportType
	 *            the report type it is interested in
	 */
	private void rememberObservable(QualifiedObservable observable, Class<?> reportType)
	{
		ArrayList<QualifiedObservable> relevantObservables = observables.get(reportType);
		if (relevantObservables == null)
		{
			relevantObservables = new ArrayList<QualifiedObservable>();
			observables.put(reportType, relevantObservables);
		}
		relevantObservables.add(observable);
	}

	/**
	 * Used when an observer wants to receive reports of a given type
	 * 
	 * @param observer
	 *            the observer who is interested
	 * @param reportType
	 *            the report type the observer wants to receive
	 */
	public void registerObserver(Observer observer, Class<?> reportType)
	{
		rememberObserver(observer, reportType);

		ArrayList<QualifiedObservable> relevantObservables = observables.get(reportType);
		if (relevantObservables != null)
		{
			for (QualifiedObservable observable : relevantObservables)
			{
				observable.addObserver(observer, reportType);
			}
		}
	}

	/**
	 * @param observer
	 *            the observer we should remember
	 * @param reportType
	 *            the report type this observer is interested in
	 */
	private void rememberObserver(Observer observer, Class<?> reportType)
	{
		ArrayList<Observer> relevantObservers = observers.get(reportType);
		if (relevantObservers == null)
		{
			relevantObservers = new ArrayList<Observer>();
			observers.put(reportType, relevantObservers);
		}
		relevantObservers.add(observer);
	}

	/**
	 * This is used when a qualified observable no longer wants to report a given report type
	 * @param observable the observable that is going quiet
	 * @param reportType the report type that will no longer be reported
	 */
	public void unregisterQualifiedObservable(QualifiedObservable observable, Class<?> reportType)
	{
		disconnectObserversFrom(observable, reportType);
		
		ArrayList<QualifiedObservable> relevantObservables = observables.get(reportType);
		if (relevantObservables != null)
		{
			relevantObservables.remove(observable);
		}
	}

	/**
	 * Disconnects all of the observers we have attached to a given observable for a given report type
	 * @param observable the observable we are disconnecting
	 * @param reportType the report type that should not longer be reported
	 */
	private void disconnectObserversFrom(QualifiedObservable observable, Class<?> reportType)
	{
		ArrayList<Observer> relevantObservers = observers.get(reportType);
		if (relevantObservers != null)
		{
			for (Observer observer : relevantObservers)
			{
				observable.deleteObserver(observer, reportType);
			}
		}
	}

	/**
	 * This is called when an observer no longer wants to receive reports of a given type
	 * @param observer the observer who is no longer interested
	 * @param reportType the report types they no longer want to receive
	 */
	public void unregisterObserver(Observer observer, Class<?> reportType)
	{

		disconnectFromObservables(observer, reportType);
		ArrayList<Observer> observerList = observers.get(reportType);
		if (observerList != null)
		{
			observerList.remove(observer);
		}
	}

	/**
	 * Disconnect an observer from all of the observables that are reporting a given report type
	 * @param observer the observer we want to disconnect
	 * @param reportType the report type that should no longer be reported
	 */
	private void disconnectFromObservables(Observer observer, Class<?> reportType)
	{
		ArrayList<QualifiedObservable> relevantObservables = observables.get(reportType);
		if (relevantObservables != null)
		{
			for (QualifiedObservable observable : relevantObservables)
			{
				observable.deleteObserver(observer, reportType);
			}
		}
	}

}
