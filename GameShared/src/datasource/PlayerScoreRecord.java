package datasource;

/**
 * A data transfer object that holds information about a player's score. Used
 * for the high score list
 * 
 * @author Merlin
 *
 */
public class PlayerScoreRecord
{
	private String playerName;
	private int experiencePoints;

	/**
	 * @param playerName the player's name
	 * @param experiencePoints the player's experience points
	 */
	public PlayerScoreRecord(String playerName, int experiencePoints)
	{
		super();
		this.playerName = playerName;
		this.experiencePoints = experiencePoints;
	}

	/**
	 * @return the player's name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @return the player's experience points
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

}
