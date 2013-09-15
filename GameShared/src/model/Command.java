package model;

/**
 * @author merlin
 *
 */
public interface Command
{

	/**
	 * perform the action associated with this command
	 * @return true of the operation was successful
	 */
	boolean execute();

}
