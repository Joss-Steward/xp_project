package model;

import data.Position;

/**
 * The players that are in the test database
 * 
 * @author Merlin
 * 
 */
public enum NpcsInDB
{
	/**
	 * 
	 */
	QUIZ_BOT(100, "Quizard", "male_a", 0, 8);
	/**
	 * 
	 */
	//OTHER_BOT(101, "Other Npc", "male_b", 4, 13);

	private int playerID;

	private String appearanceType;

	private int row;

	private int col;

	private String playerName;

	NpcsInDB(int id, String playerName, String type, int row, int col)
	{
		this.playerID = id;
		this.playerName = playerName;
		this.appearanceType = type;
		this.row = row;
		this.col = col;
	}

	/**
	 * Get the player's unique name
	 * 
	 * @return the name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * Get the player's player type
	 * 
	 * @return a string that matches the name of one of the members of the enum
	 *         PlayerTypes
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * Get the player's unique ID
	 * 
	 * @return the id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * get this player's most recent position
	 * 
	 * @return this player's position
	 */
	public Position getPosition()
	{
		return new Position(row, col);
	}

}