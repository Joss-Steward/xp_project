package edu.ship.shipsim.areaserver.datasource;

/**
 * A data transfer record that contains the state of one adventure for one player
 * @author Carol
 *
 */
public class AdventureStateRecord
{

	private int playerID;
	private int questID;
	private int adventureID;
	private AdventureStateList state;

	/**
	 * @param playerID the player
	 * @param questID the quest that contains the adventure
	 * @param adventureID the adventure
	 * @param state the player's state for that adventure
	 */
	public AdventureStateRecord(int playerID, int questID, int adventureID,
			AdventureStateList state)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.state = state;
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
	 * @return the adventure ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the state
	 */
	public AdventureStateList getState()
	{
		return state;
	}

}
