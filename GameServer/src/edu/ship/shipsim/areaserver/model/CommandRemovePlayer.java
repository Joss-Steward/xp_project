package edu.ship.shipsim.areaserver.model;


/**
 * Command used to remove a player from this area server's part of the game
 * 
 * @author nhydock
 * 
 */
public class CommandRemovePlayer extends Command
{

	private int playerID;

	/**
	 * 
	 * @param playerID
	 *            the player's player id
	 */
	public CommandRemovePlayer(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * 
	 * @see edu.ship.shipsim.areaserver.model.Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().removePlayer(playerID);
		QuestManager.getSingleton().removeQuestStatesForPlayer(playerID);
		return true;
	}

}
