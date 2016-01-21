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
	 * @see Command#execute()
	 */
	@Override
	protected boolean execute() 
	{

		ClientPlayerManager.getSingleton().loginFailed();
		
		return true;
	}

}
