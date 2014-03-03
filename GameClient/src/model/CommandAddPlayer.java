package model;

/**
 * Command the puts a new player in the system when that new player joins our
 * area server.
 * 
 * @author merlin
 * 
 */
public class CommandAddPlayer extends Command
{

	private int playerID;
	private String playerName;

	/**
	 * For now, we just know his name
	 * @param playerID the unique player name of the new player
	 * @param playerName this player's name
	 */
	public CommandAddPlayer(int playerID, String playerName)
	{
		this.playerID = playerID;
		this.playerName = playerName;
	}

	@Override
	protected boolean execute()
	{
		PlayerManager manager = PlayerManager.getSingleton();
		Player p = null;
		if(manager.getThisClientsPlayer().getID() == this.playerID)
		{
			//The player message is about this clients player
			//TODO: This needs to be done when ThisClientsPlayer is a Player
			//p = manager.getThisClientsPlayer();
		}
		else
		{
			p = PlayerManager.getSingleton().addPlayer(playerID);
		}
		p.setPlayerName(playerName);
		return true;
	}

}
