package model;

/**
 * @author merlin
 *
 */
public class CommandExecutor
{

	private static CommandExecutor singleton;

	/**
	 * @return the only instance of this class
	 */
	public synchronized static CommandExecutor getSingleton()
	{
		if (singleton == null)
		{
			singleton = new CommandExecutor();
		}
		return singleton;
	}

	/**
	 * Execute a given command
	 * @param c the command to execute
	 */
	public void executeCommand(Command c)
	{
		c.execute();
	}

}
