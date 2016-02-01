package model;

import data.Position;
/**
 * @author Matt Kujawski
 */
public class CommandMovePlayer extends Command
{
	private int thePlayerID;
	private Position thePosition;
	private boolean isTeleporting;

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
		isTeleporting = false;
	}

	/**
	 * Moves a certain Player into the designated position. If the player moving
	 * is this client's player, we check to see if the move is legal and
	 * initiate teleportation if appropriate. If the player is another player,
	 * we have just been informed that they moved and we should just put them
	 * there.
	 * 
	 * @see Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		boolean moved = false;

		ThisClientsPlayer thisClientsPlayer = ClientPlayerManager.getSingleton()
				.getThisClientsPlayer();
		if (thisClientsPlayer.getID() == thePlayerID)
		{
			if (MapManager.getSingleton().getIsTileTeleport(thePosition))
			{
				thisClientsPlayer.teleport(thePosition);
				isTeleporting = true;
			}

			if (MapManager.getSingleton().getIsTilePassable(thePosition))
			{
				thisClientsPlayer.move(thePosition);
				moved = true;
			}
		} else
		{
			ClientPlayerManager.getSingleton().getPlayerFromID(thePlayerID)
					.move(thePosition);
			moved = true;
		}
		return moved;
	}

	/**
	 * Only dump when teleporting
	 */
	protected boolean doDump()
	{
		return isTeleporting;
	}
}
