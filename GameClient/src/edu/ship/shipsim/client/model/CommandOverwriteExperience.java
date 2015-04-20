/**
 * 
 */
package edu.ship.shipsim.client.model;

import communication.messages.InitializeThisClientsPlayerMessage;
import datasource.LevelRecord;

/**
 * @author nk3668
 *
 */
public class CommandOverwriteExperience extends Command
{
	private int experience;
	private LevelRecord record;
	private int playerID;
	
	/**
	 * constructor for the command
	 * @param msg InitializeClientsPlayerMessage which contains all the data to
	 * initialize this client player
	 */
	public CommandOverwriteExperience(InitializeThisClientsPlayerMessage msg) 
	{
		this.experience = msg.getExperiencePts();
		this.record = msg.getLevel();
	}

	/**
	 * @param experiencePoints player's experience points
	 * @param record level record of the player
	 */
	public CommandOverwriteExperience(int playerID, int experiencePoints, LevelRecord record) 
	{
		this.playerID = playerID;
		this.record = record;
		this.experience = experiencePoints;
	}
	
	/**
	 * Overwrites the player's experience
	 */
	@Override
	protected boolean execute() 
	{
		ThisClientsPlayer thisClientsPlayer = PlayerManager.getSingleton().getThisClientsPlayer();
		thisClientsPlayer.overwriteExperiencePoints(experience, record);
		return true;
	}

	/**
	 * retrieves the experience
	 * @return experience the client player's current experience
	 */
	public int getExperiencePoints() 
	{
		return experience;
	}

	/**
	 * retrieves the LevelRecord
	 * @return record the client player's current Level Record
	 */
	public LevelRecord getLevelRecord() 
	{
		return record;
	}

	public int getPlayerID() 
	{
		return playerID;
	}
}
