package edu.ship.shipsim.client.model;


/**
 * Command to reset the entire client model state
 * @author Steve
 *
 */
public class CommandClearModelState extends Command 
{

	/**
	 * 
	 * @see edu.ship.shipsim.client.model.Command#execute()
	 */
	protected boolean execute() 
	{
		PlayerManager.resetSingleton();
		MapManager.resetSingleton();
		return true;
	}

}
