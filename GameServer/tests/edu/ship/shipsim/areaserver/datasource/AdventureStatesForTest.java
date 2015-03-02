package edu.ship.shipsim.areaserver.datasource;



/**
 * Creates adventures for the DB
 * @author merlin
 *
 */
public enum AdventureStatesForTest
{
	/**
	 * 
	 */
	PLAYER1_QUEST1_ADV1(1, 1, 1, AdventureStateList.TRIGGERED),
	/**
	 * 
	 */
	PLAYER1_QUEST1_ADV2(1, 1, 2, AdventureStateList.COMPLETED),
	/**
	 * 
	 */
	PLAYER1_QUEST2_ADV1(1, 2, 1, AdventureStateList.COMPLETED),
	/**
	 * 
	 */
	PLAYER1_QUEST2_ADV2(1, 2, 2, AdventureStateList.TRIGGERED),
	/**
	 * 
	 */
	PLAYER2_QUEST1_ADV1(2, 1, 1, AdventureStateList.TRIGGERED),
	/**
	 * 
	 */
	PLAYER2_QUEST1_ADV2(2, 1, 2, AdventureStateList.TRIGGERED);

	
	private int adventureID;
	private int questID;
	private int playerID;
	private AdventureStateList state;
	
	/**
	 * Constructor for Adventures Enum
	 * @param adventureID this adventure's unique ID
	 * @param questID the ID of the quest that contains this adventure
	 */
	AdventureStatesForTest(int playerID, int questID, int adventureID,  AdventureStateList state)
	{
		this.adventureID = adventureID;
		this.questID = questID;
		this.playerID = playerID;
		this.state = state;
	}

	/**
	 * @return the adventureID
	 */
	public int getAdventureID()
	{
		return adventureID;
	}


	/**
	 * 
	 * @return the player's ID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * 
	 * @return the current state of the adventure for that player
	 */
	public AdventureStateList getState()
	{
		return state;
	}

	/**
	 * @return the questID
	 */
	public int getQuestID()
	{
		return questID;
	}
	
}
