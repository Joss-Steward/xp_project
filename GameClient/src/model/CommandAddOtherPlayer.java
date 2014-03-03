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
	private String playerName;

	/**
	 * For now, we just know his name
	 * @param playerID the unique player name of the new player
	 * @param playerName this player's name
	 */
	public CommandAddOtherPlayer(int playerID, String playerName)
	{
		this.playerID = playerID;
		this.playerName = playerName;
	}

	@Override
	protected boolean execute()
	{
		Player p = PlayerManager.getSingleton().addPlayer(playerID);
		p.setName(playerName);
		return true;
	}

}
