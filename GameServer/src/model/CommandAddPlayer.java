package model;

import datasource.DatabaseException;

/**
 * Command used to add a new player to this area server's part of the game
 * 
 * @author Merlin
 * 
 */
public class CommandAddPlayer extends Command
{

	private int playerID;
	private double pin;

	/**
	 * 
	 * @param playerID the player's player id
	 * @param pin the player's pin
	 */
	public CommandAddPlayer(int playerID, double pin)
	{
		this.playerID = playerID;
		this.pin = pin;
	}

	/**
	 * 
	 * @see model.Command#execute()
	 */
	@Override
	protected boolean execute()
	{
		try
		{
			PlayerManager.getSingleton().addPlayer(playerID, pin);
		} catch (DatabaseException | IllegalQuestChangeException e)
		{
			e.printStackTrace();
		}
		return true;
	}

}
