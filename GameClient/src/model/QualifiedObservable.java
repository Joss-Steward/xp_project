package model;

import java.util.ArrayList;
import java.util.HashMap;

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
public abstract class QualifiedObservable
{

	private HashMap<ObserverQualifier, ArrayList<QualifiedObserver>> observers = new HashMap<ObserverQualifier, ArrayList<QualifiedObserver>>();

	/**
	 * Used to determine whether this object reports information related to a
	 * given qualifier
	 * 
	 * @param qualifier
	 *            the qualifier we are interested in
	 * @return true if we report information relevant to that qualifier
	 */
	public abstract boolean notifiesOn(ObserverQualifier qualifier);

	/**
	 * @param observer
	 *            the observer we are adding
	 * @param qualifier
	 *            the condition under which they want to be notified
	 */
	public void addObserver(QualifiedObserver observer, ObserverQualifier qualifier)
	{
		if (!this.notifiesOn(qualifier))
		{
			throw new IllegalArgumentException("Attempt to register a listener with qualifier "
					+ qualifier + " for class " + this.getClass()
					+ " which does not notify for tyat type of information");
		}
		ArrayList<QualifiedObserver> relevantObservers = observers.get(qualifier);
		if (relevantObservers == null)
		{
			relevantObservers = new ArrayList<QualifiedObserver>();
			observers.put(qualifier, relevantObservers);
		}
		relevantObservers.add(observer);
	}

	/**
	 * @param arg
	 *            the argument we want to send to the observers
	 * @param qualifier
	 *            the qualifier specifying the type of information we are
	 *            sending
	 */
	public void notifyObservers(Object arg, ObserverQualifier qualifier)
	{
		ArrayList<QualifiedObserver> relevantObservers = observers.get(qualifier);
		for (QualifiedObserver observer : relevantObservers)
		{
			observer.update(this, arg);
		}

	}

	/**
	 * Deletes a remover from receiving notifications of a given qualifier.
	 * 
	 * @param observer
	 *            the observer who is no longer interested in something
	 * @param qualifier
	 *            the type of information they no longer want to receive
	 */
	public void removeObserver(QualifiedObserver observer, ObserverQualifier qualifier)
	{
		ArrayList<QualifiedObserver> relevantObservers = observers.get(qualifier);
		relevantObservers.remove(observer);
	}

}
