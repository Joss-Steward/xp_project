package model;

/**
 * Command to reset the entire client model state
 * @author Steve
 *
 */
public class CommandClearModelState extends Command 
{

	protected boolean execute() 
	{
		PlayerManager.resetSingleton();
		MapManager.resetSingleton();
		return true;
	}

}
