package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * This is an implementation of the observer pattern that allows observers to
 * specify the type of information that they would like to receive. In cases
 * where an object can notify under a wide variety of conditions, this allows
 * the observers to specify which types of notification they want to receive so
 * they are not overwhelmed with extraneous information.
 * 
 * @author Merlin
 * 
 */
public abstract class QualifiedObservable extends Observable
{

	private HashMap<Class<? extends QualifiedObservableReport>, ArrayList<Observer>> observers = new HashMap<Class<? extends QualifiedObservableReport>, ArrayList<Observer>>();
	protected ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<Class<? extends QualifiedObservableReport>>();

	/**
	 * Used to determine whether this object reports information related to a
	 * given qualifier
	 * 
	 * @param reportType
	 *            the report type we are interested in
	 * @return true if we report information relevant to that qualifier
	 */
	public boolean notifiesOn(Class<?> reportType)
	{
		return reportTypes.contains(reportType);
	}

	/**
	 * @param observer
	 *            the observer we are adding
	 * @param reportType
	 *            the condition under which they want to be notified
	 */
	public void addObserver(Observer observer,
			Class<? extends QualifiedObservableReport> reportType)
	{
		if (!this.notifiesOn(reportType))
		{
			throw new IllegalArgumentException(
					"Attempt to register a listener with reportType "
							+ reportType.getCanonicalName() + " for class "
							+ this.getClass()

							+ " which does not notify for tyat type of information");
		}
		ArrayList<Observer> relevantObservers = observers.get(reportType);
		if (relevantObservers == null)
		{
			relevantObservers = new ArrayList<Observer>();
			observers.put(reportType, relevantObservers);
		}
		relevantObservers.add(observer);
	}

	/**
	 * @param arg
	 *            the argument we want to send to the observers
	 */
	public void notifyObservers(QualifiedObservableReport arg)
	{
		ArrayList<Observer> relevantObservers = observers.get(arg.getClass());
		if (relevantObservers != null)
		{
			for (Observer observer : relevantObservers)
			{
				observer.update(this, arg);
			}
		}
	}

	/**
	 * Deletes a remover from receiving notifications of a given qualifier.
	 * 
	 * @param observer
	 *            the observer who is no longer interested in something
	 * @param reportType
	 *            the type of information they no longer want to receive
	 */
	public void deleteObserver(Observer observer, Class<?> reportType)
	{
		ArrayList<Observer> relevantObservers = observers.get(reportType);
		relevantObservers.remove(observer);
	}

	/**
	 * Used for testing purposes
	 * 
	 * @param reportType
	 *            the type of report we are interested in
	 * @return the number of observers we have
	 */
	public int countObservers(Class<?> reportType)
	{
		ArrayList<Observer> relevantObservers = observers.get(reportType);
		if (relevantObservers == null)
		{
			return 0;
		}
		return relevantObservers.size();
	}

	/**
	 * Get a list of the report types that we might be notifying with
	 * 
	 * @return that list (should never be null)
	 */
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWeSend()
	{
		return reportTypes;
	}

	protected void registerReportTypesWeNotify()
	{
		for (Class<? extends QualifiedObservableReport> reportType : reportTypes)
		{
			QualifiedObservableConnector.getSingleton().registerQualifiedObservable(this,
					reportType);
		}
	}

	protected void unregisterReportTypesWeNotify()
	{
		for (Class<? extends QualifiedObservableReport> reportType : reportTypes)
		{
			QualifiedObservableConnector.getSingleton().unregisterQualifiedObservable(this,
					reportType);
		}
	}
}
