package model;

/**
 * @author merlin
 * 
 */
public abstract class Command implements InfoPacket
{

	/**
	 * perform the action associated with this command
	 * 
	 * @return true of the operation was successful
	 */
	protected abstract boolean execute();

}
