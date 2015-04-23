package edu.ship.shipsim.areaserver.model;

/**
 * @author Ryan
 *
 */
public class CommandAdventureNotificationComplete extends Command
{
	
	private int playerID;
	private int questID;
	private int adventureID;

	/**
	 * Constructor
	 * @param playerID id of the player
	 * @param questID id of the quest
	 * @param adventureID id of the adventure
	 */
	public CommandAdventureNotificationComplete(int playerID, int questID, int adventureID) 
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
	}

	@Override
	protected boolean execute() 
	{
		return false;
	}

	/**
	 * @return id of the player
	 */
	public int getPlayerID() 
	{
		return playerID;
	}

	/**
	 * @return id of the quest
	 */
	public int getQuestID() 
	{
		return questID;
	}

	/**
	 * @return id of the adventure
	 */
	public int getAdventureID() 
	{
		return adventureID;
	}

	
}
