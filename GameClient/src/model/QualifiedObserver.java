
package model;

/**
 * This interface is required of all objects that want to observe QualifiedObservables
 * @author Merlin
 *
 */
public interface QualifiedObserver
{

	/**
	 * This method will be called when the QualifiedObservable we are watching changes state
	 * @param arg a (possibly null) object describing what has changed
	 * @param obs the QualifiedObservable that is notifying
	 */
	void update(QualifiedObservable obs, Object arg);
}
