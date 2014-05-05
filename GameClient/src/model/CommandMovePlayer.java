package model;

import data.Position;

/**
 * @author Matt Kujawski
 */
public class CommandMovePlayer extends Command
{
	private int thePlayerID;
	private Position thePosition;

	/**
	 * @param playerID
	 *            is the name of the player
	 * @param position
	 *            is the position of the player
	 */
	public CommandMovePlayer(int playerID, Position position)
	{
		thePlayerID = playerID;
		thePosition = position;
	}

	/**
	 * Moves a certain Player into the designated position if move is legal.
	 */
	@Override
	protected boolean execute()
	{
		boolean moved = false;
		
		if(MapManager.getSingleton().getIsTileTeleport(thePosition))
		{
			
			PlayerManager.getSingleton().getPlayerFromID(thePlayerID).teleport(thePosition);
		}

		if (MapManager.getSingleton().getIsTilePassable(thePosition))
		{
			PlayerManager.getSingleton().getPlayerFromID(thePlayerID).move(thePosition);
			moved = true;
		}

		return moved;
	}
}
