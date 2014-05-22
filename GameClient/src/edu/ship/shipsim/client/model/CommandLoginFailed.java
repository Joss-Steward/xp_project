package edu.ship.shipsim.client.model;



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

		PlayerManager.getSingleton().loginFailed();
		
		return true;
	}

}
