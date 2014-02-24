package model;

import data.Position;

/**
 * @author Matt Kujawski
 *
 */
public class CommandMoveOtherPlayer extends Command
{
	private String thePlayerName;
	private Position thePosition;
	
	/**
	 * @param playerName is the name of the player
	 * @param position is the position of the player
	 */
	public CommandMoveOtherPlayer(String playerName, Position position)
	{
		thePlayerName = playerName;
		thePosition = position;
	}

	@Override
	protected boolean execute() 
	{
		PlayerManager.getSingleton().getPlayerNamed(thePlayerName).move(thePosition);
		return true;
	}
	
	
}
