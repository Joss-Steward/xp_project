package model;

/**
 * Command to the model signifying the a player has been disconnected from the
 * area server that the player is on, and no longer needs to be watched by this client
 * 
 * @author nhydock
 */
public class CommandRemovePlayer extends Command {

	private int playerID;
	
	/**
	 * @param id
	 *  the id of the player
	 */
	public CommandRemovePlayer(int id)
	{
		this.playerID = id;
	}
	
	@Override
	protected boolean execute() {
		PlayerManager.getSingleton().removePlayer(playerID);
		return true;
	}

}
