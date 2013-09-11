
package model;

/**
 * @author Merlin
 *
 */
public interface QualifiedObserver
{

	/**
	 * @param obs
	 * @param string
	 */
	void update(QualifiedObservable obs, Object arg);
}
