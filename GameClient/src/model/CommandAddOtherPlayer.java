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

	private int playerID;

	/**
	 * For now, we just know his name
	 * @param playerID the unique player name of the new player
	 */
	public CommandAddOtherPlayer(int playerID)
	{
		this.playerID = playerID;
	}

	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().addPlayer(playerID);
		return true;
	}

}
