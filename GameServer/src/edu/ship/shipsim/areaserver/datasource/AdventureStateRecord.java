package edu.ship.shipsim.areaserver.datasource;

public class AdventureStateRecord
{

	private int playerID;
	private int questID;
	private int adventureID;
	private AdventureStateList state;

	public AdventureStateRecord(int playerID, int questID, int adventureID,
			AdventureStateList state)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.state = state;
	}

	public int getPlayerID()
	{
		return playerID;
	}

	public int getQuestID()
	{
		return questID;
	}

	public int getAdventureID()
	{
		return adventureID;
	}

	public AdventureStateList getState()
	{
		return state;
	}

}
