package edu.ship.shipsim.areaserver.model;

import datasource.DatabaseException;


/**
 * Persist a player through a command
 * 
 * @author Steve
 *
 */
public class CommandPersistPlayer extends Command
{
	private int playerID;
	
	/**
	 * 
	 * @param playerID The playerID to persist
	 */
	public CommandPersistPlayer(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.model.Command#execute()
	 */
	@Override
	protected boolean execute() 
	{
		try
		{
			return PlayerManager.getSingleton().persistPlayer(playerID);
		} catch (DatabaseException e)
		{
			return false;
		}
	}

}
