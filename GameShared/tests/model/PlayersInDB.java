package model;

import data.Position;

/**
 * The players that are in the test database
 * 
 * @author Merlin
 * 
 */
public enum PlayersInDB
{
	/**
	 * 
	 */
	JOHN(1, "John", "male_a", 0, 8, "pw"),
	/**
	 * 
	 */
	MERLIN(2, "Merlin", "male_b", 4, 13, "pw"),
	/**
	 * 
	 */
	NICK(3, "Nick", "magi", 4, 13, "pw"),
	/**
	 * 
	 */
	JOSH(4, "Josh", "ninja", 4, 13, "pw"),
	/**
	 * 
	 */
	MATT(2, "Matt", "male_b", 4, 13, "pw"),
	/**
	 * 
	 */
	STEVE(2, "Steve", "knight_with_straw_hat", 4, 13, "pw"),
	/**
	 * 
	 */
	FRANK(2, "Frank", "male_b", 4, 13, "pw"),
	/**
	 * 
	 */
	GA(2, "Ga", "male_b", 4, 13, "pw"),
	/**
	 * 
	 */
	ANDY(2, "Andy", "male_b", 4, 13, "pw"),
	/**
	 * 
	 */
	DAVE(2, "Dave", "male_b", 4, 13, "pw");

	private int playerID;

	private String appearanceType;

	private int row;

	private int col;

	private String playerName;
	
	private String password;

	PlayersInDB(int id, String playerName, String type, int row, int col, String password)
	{
		this.playerID = id;
		this.playerName = playerName;
		this.appearanceType = type;
		this.row = row;
		this.col = col;
		this.password = password;
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

	/**
	 * 
	 * @return the player's password
	 */
	public String getPlayerPassword() 
	{
		return password;
	}

}