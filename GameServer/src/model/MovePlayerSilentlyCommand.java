package model;

import data.Position;

/**
 * Changes a player's position without notifying the rest of the players on the server.  
 * @author Merlin
 *
 */
public class MovePlayerSilentlyCommand extends Command
{

	private Position newPosition;
	private int playerId;

	/**
	 * 
	 * @param playerId the player who is moving
	 * @param position the position they should be moving to
	 */
	public MovePlayerSilentlyCommand(int playerId, Position position)
	{
		this.newPosition = position;
		this.playerId = playerId;
	}

	@Override
	protected boolean execute()
	{
		Player player = PlayerManager.getSingleton().getPlayerFromID(playerId);
		if (player != null)
		{
			player.setPlayerPositionWithoutNotifying(newPosition);
			return true;
		} else
		{
			return false;
		}
	}

}
