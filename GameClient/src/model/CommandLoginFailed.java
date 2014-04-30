package model;


/**
 * @author Dave, Andy, Matt
 *
 *Creates the CommandLoginFailed
 *
 */
public class CommandLoginFailed extends Command
{

/**
 * Executes the loginFailed which fires the logInFailedReport
 * 
 */
	@Override
	public boolean execute() 
	{

		PlayerManager.getSingleton().loginFailed();
		
		return true;
	}

}
