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

	private String playerName;

	/**
	 * For now, we just know his name
	 * @param playerName the unique user name of the new player
	 */
	public CommandAddOtherPlayer(String playerName)
	{
		this.playerName = playerName;
	}

	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().addPlayer(playerName);
		return true;
	}

}
