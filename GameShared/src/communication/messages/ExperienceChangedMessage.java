package communication.messages;

import java.io.Serializable;

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
	private int playerID;

	/**
	 * @param experiencePoints the amount of experience points the player has
	 * @param playerID the id of the player
	 */
	public ExperienceChangedMessage(int experiencePoints, int playerID) 
	{
		this.experiencePoints = experiencePoints;
		this.playerID = playerID;
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
	public int getPlayerID() 
	{
		return playerID;
	}

}
