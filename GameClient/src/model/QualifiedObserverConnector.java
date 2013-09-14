package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Merlin
 *
 */
public class QualifiedObserverConnector
{

	private static QualifiedObserverConnector singleton;
	
	private HashMap<Class<?>, ArrayList<QualifiedObservable>> observables;
	private HashMap<Class<?>, ArrayList<QualifiedObserver>> observers;
	
	private QualifiedObserverConnector()
	{
		observables = new HashMap<Class<?>, ArrayList<QualifiedObservable>>();
		observers = new HashMap<Class<?>, ArrayList<QualifiedObserver>>();
	}
	
	/**
	 * @return the only one of these in the syste
	 */
	public synchronized static QualifiedObserverConnector getSingleton()
	{
		if(singleton == null)
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
	 * This is used by an observable to tell the world that he will report a given report type
	 * @param observable the object who wants to be observed
	 * @param reportType the type of message it will report
	 */
	public void registerQualifiedObservable(QualifiedObservable observable,
			Class<?> reportType)
	{
		rememberObservable(observable, reportType);
		
		ArrayList<QualifiedObserver> relevantObservers = observers.get(reportType);
		if (relevantObservers != null)
		{
			for(QualifiedObserver observer:relevantObservers)
			{
				observable.addObserver(observer, reportType);
			}
		}
	}
	/**
	 * @param observable the observable we should remember
	 * @param reportType the report type it is interested in
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
	 * @param observer the observer who is interested
	 * @param reportType the report type the observer wants to receive
	 */
	public void registerQualifiedObserver(QualifiedObserver observer, Class<?> reportType)
	{
		rememberObserver(observer, reportType);
		
		ArrayList<QualifiedObservable> relevantObservables = observables.get(reportType);
		if (relevantObservables != null)
		{
			for(QualifiedObservable observable:relevantObservables)
			{
				observable.addObserver(observer, reportType);
			}
		}
	}
	
	/**
	 * @param observer the observer we should remember
	 * @param reportType the report type this observer is interested in
	 */
	private void rememberObserver(QualifiedObserver observer, Class<?> reportType)
	{
		ArrayList<QualifiedObserver> relevantObservers = observers.get(reportType);
		if (relevantObservers == null)
		{
			relevantObservers = new ArrayList<QualifiedObserver>();
			observers.put(reportType, relevantObservers);
		}
		relevantObservers.add(observer);
	}

}
