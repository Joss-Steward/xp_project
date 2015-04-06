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
	 * Overwrites the player's experience
	 */
	@Override
	protected boolean execute() 
	{
		PlayerManager.getSingleton().getThisClientsPlayer().overwriteExperiencePoints(experience, record);
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
	

}
