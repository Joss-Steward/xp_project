package model;

import data.Position;

/**
 * @author Matt Kujawski
 *
 */
public class CommandMovePlayer extends Command
{
	private int thePlayerID;
	private Position thePosition;
	
	/**
	 * @param playerID is the name of the player
	 * @param position is the position of the player
	 */
	public CommandMovePlayer(int playerID, Position position)
	{
		thePlayerID = playerID;
		thePosition = position;
	}

	@Override
	protected boolean execute() 
	{
		PlayerManager.getSingleton().getPlayerFromID(thePlayerID).move(thePosition);
		return true;
	}
	
	
}
