package communication.messages;

import java.io.Serializable;

import data.AdventureStateEnum;

/**
 * A message from an area server to a client telling the client to notify the
 * player that they has fulfilled an adventure
 * @author Ryan
 *
 */
public class AdventureStateChangeMessage implements Message, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int playerID;
	private int questID;
	private int adventureID;
	private String adventureDescription;
	private AdventureStateEnum newState;


	/**
	 * @param playerID
	 * 		the current player's id
	 * @param questID 
	 * 		the quest id
	 * @param adventureID
	 * 		the id of the adventure
	 * @param adventureDescription
	 * 		the description of the adventure
	 * @param newState
	 * 		the new state the adventure will be in 
	 */
	public AdventureStateChangeMessage(int playerID, int questID, int adventureID, String adventureDescription,
			AdventureStateEnum newState)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.newState = newState;
	}

	/**
	 * @return the adventure's descrption
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}

	/**
	 * @return the adventure's ID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the state the quest has moved to
	 */
	public AdventureStateEnum getNewState()
	{
		return newState;
	}

	/**
	 * Get the player's ID
	 * @return playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}
	
	/**
	 * Get the quest's ID
	 * @return questID
	 */
	public int getQuestID()
	{
		return questID;
	}
}
