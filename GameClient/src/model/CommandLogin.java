package model;

/**
 * @author Merlin
 *
 */
public class CommandLogin implements Command
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
	public boolean execute()
	{
		Player p = Player.getSingleton();
		p.initiateLogin(name, password);
		return true;
	}

}
