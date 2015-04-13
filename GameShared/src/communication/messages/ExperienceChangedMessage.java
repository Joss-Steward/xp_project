package communication.messages;

/**
 * ExperienceChangeMessage class 
 * @author Olivia
 * @author LaVonne
 */
public class ExperienceChangedMessage 
{

	private int experiencePoints;
	private int playerID;

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
