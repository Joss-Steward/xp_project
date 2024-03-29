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

	/**
	 * @see Command#execute()
	 */
	@Override
	protected boolean execute() {
		ClientPlayerManager.getSingleton().removePlayer(playerID);
		return true;
	}
	
	/**
	 * 
	 * @return the ID of the player who left this area
	 */
	public int getPlayerID()
	{
		return playerID;
	}

}
