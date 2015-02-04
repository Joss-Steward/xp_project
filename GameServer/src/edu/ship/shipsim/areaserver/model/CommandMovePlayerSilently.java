package edu.ship.shipsim.areaserver.model;

import data.Position;

/**
 * Changes a player's position without notifying the rest of the players on the server.  
 * @author Merlin
 *
 */
public class CommandMovePlayerSilently extends Command
{

	private Position newPosition;
	private int playerId;
	private String mapName;

	/**
	 * 
	 * @param mapName the name of the map the player should be on
	 * @param playerId the player who is moving
	 * @param position the position they should be moving to
	 */
	public CommandMovePlayerSilently(String mapName, int playerId, Position position)
	{
		this.mapName = mapName;
		this.newPosition = position;
		this.playerId = playerId;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.model.Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		Player player = PlayerManager.getSingleton().getPlayerFromID(playerId);
		if (player != null)
		{
			player.setPlayerPositionWithoutNotifying(newPosition);
			player.setMapName(mapName);
			return true;
		} else
		{
			return false;
		}
	}

}
