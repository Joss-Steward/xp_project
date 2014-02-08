package model;

/**
 * @author Merlin
 *
 */
public class CommandLogin extends Command
{

	private String name;
	private String password;

	/**
	 * @param name the username
	 * @param password the password
	 */
	public CommandLogin(String name, String password)
	{
		this.name = name;
		this.password = password;
	}

	/**
	 * @see model.Command#execute()
	 */
	@Override
	protected
	boolean execute()
	{
		ThisClientsPlayer p = PlayerManager.getSingleton().getThisClientsPlayer();
		p.initiateLogin(name, password);
		return true;
	}

}
