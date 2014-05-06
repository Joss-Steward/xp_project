package model;


/**
 * @author Andy and Matt
 *
 *Creates the CommandPinFailed
 *
 */
public class CommandPinFailed extends Command
{
	private final String err;
	
	/**
	 * @param msg is the error message
	 */
	public CommandPinFailed(String msg)
	{
		err = msg;
	}

/**
 * Executes the PinFailed which fires the PinFailedReport
 * 
 */
	@Override
	public boolean execute() 
	{
		PlayerManager.getSingleton().pinFailed(err);
		
		return true;
	}

}
