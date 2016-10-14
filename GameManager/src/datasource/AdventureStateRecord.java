package datasource;

import datatypes.AdventureStateEnum;


/**
 * A data transfer record that contains the state of one adventure for one
 * player
 * 
 * @author Merlin
 *
 */
public class AdventureStateRecord
{

	private int playerID;
	private int questID;
	private int adventureID;
	private int experiencePoints;
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	private AdventureStateEnum state;
	

	/**
	 * @param playerID
	 *            the player
	 * @param questID
	 *            the quest that contains the adventure
	 * @param adventureID
	 *            the adventure
	 * @param state
	 *            the player's state for that adventure
	 * @param needingNotification
	 *            true if the player should be notified about this adventure state
	 */
	public AdventureStateRecord(int playerID, int questID, int adventureID,
			AdventureStateEnum state, int experiencePoints)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.state = state;
		this.experiencePoints = experiencePoints;
	}

	/**
	 * @return the adventure ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the player's ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the quest ID
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return the state
	 */
	public AdventureStateEnum getState()
	{
		return state;
	}

	/**
	 * @param newState
	 *            the state this adventure should have
	 */
	public void setState(AdventureStateEnum newState)
	{
		this.state = newState;
	}
}
