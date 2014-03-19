package model;

import data.Position;

/**
 * Command used to move a player on the server's part of the game
 * 
 * @author Frank
 * 
 */
public class MovePlayerCommand extends Command
{
	private int playerId;
	private Position newPosition;

	/**
	 * @param playerId
	 *            The player who to move
	 * @param newPosition
	 *            The new Position to move
	 */
	public MovePlayerCommand(int playerId, Position newPosition)
	{
		this.playerId = playerId;
		this.newPosition = newPosition;
	}

	@Override
	protected boolean execute()
	{
		Player player = PlayerManager.getSingleton().getPlayerFromID(playerId);
		if (player != null)
		{
			player.setPlayerPosition(newPosition);
			return true;
		} else
		{
			return false;
		}
	}
}
