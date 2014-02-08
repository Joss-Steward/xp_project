package model;

/**
 * Command the puts a new player in the system when that new player joins our
 * area server.
 * 
 * @author merlin
 * 
 */
public class CommandAddOtherPlayer extends Command
{

	private String userName;

	/**
	 * For now, we just know his name
	 * @param userName the unique user name of the new player
	 */
	public CommandAddOtherPlayer(String userName)
	{
		this.userName = userName;
	}

	@Override
	protected boolean execute()
	{
		Player p = new Player(userName);
		PlayerManager.getSingleton().addPlayer(p);
		return true;
	}

}
