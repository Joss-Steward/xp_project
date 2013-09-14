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
	 * @return
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
	 * @param observable
	 * @param reportType
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
	 * @param observable
	 * @param reportType
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
	 * @param observer
	 * @param reportType
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
	 * @param observer
	 * @param reportType
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
