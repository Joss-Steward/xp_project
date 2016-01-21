package model;

import data.Position;

/**
 * Command the puts a new player in the system when that new player joins our
 * area server.
 * 
 * @author merlin
 * 
 */
public class CommandInitializePlayer extends Command
{

	private int playerID;
	private String playerName;
	private String appearanceType;
	private Position position;

	/**
	 * For now, we just know his name
	 * 
	 * @param playerID
	 *            the unique player name of the new player
	 * @param playerName
	 *            this player's name
	 * @param appearanceType
	 *            The appearance type of this player
	 * @param position
	 *            The position of this player
	 */
	public CommandInitializePlayer(int playerID, String playerName,
			String appearanceType, Position position)
	{
		this.playerID = playerID;
		this.playerName = playerName;
		this.appearanceType = appearanceType;
		this.position = position;
	}

	/**
	 * @see Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		PlayerManager.getSingleton().initializePlayer(playerID, playerName,
				appearanceType, position);
		return true;
	}

}
