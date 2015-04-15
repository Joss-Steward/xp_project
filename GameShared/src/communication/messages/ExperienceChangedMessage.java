package communication.messages;

import java.io.Serializable;

import datasource.LevelRecord;

/**
 * ExperienceChangeMessage class 
 * @author Olivia
 * @author LaVonne
 */
public class ExperienceChangedMessage implements Message, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int experiencePoints;
	private LevelRecord level;

	/**
	 * @param experiencePoints the amount of experience points the player has
	 * @param levelRecord the id of the player
	 */
	public ExperienceChangedMessage(int experiencePoints, LevelRecord levelRecord) 
	{
		this.experiencePoints = experiencePoints;
		this.level = levelRecord;
	}

	/**
	 * Gets player's current experience points
	 * @return experiencePoints
	 */
	public int getExperiencePoints() 
	{
		return experiencePoints;
	}

	/**
	 * Gets player's ID
	 * @return playerID
	 */
	public LevelRecord getLevel() 
	{
		return level;
	}

}
