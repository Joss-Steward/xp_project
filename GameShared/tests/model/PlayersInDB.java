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
	JOHN(1, "John", "male_a", 0, 8, "pw", "quiznasium.tmx"),
	/**
	 * 
	 */
	MERLIN(2, "Merlin", "merlin", 4, 13, "pw", null),
	/**
	 * 
	 */
	NICK(3, "Nick", "magi", 4, 13, "pw", null),
	/**
	 * 
	 */
	JOSH(4, "Josh", "ninja", 4, 13, "pw", null),
	/**
	 * 
	 */
	MATT(5, "Matt", "male_b", 4, 13, "pw", null),
	/**
	 * 
	 */
	STEVE(6, "Steve", "knight_with_straw_hat", 4, 13, "pw", null),
	/**
	 * 
	 */
	FRANK(7, "Frank", "male_b", 4, 13, "pw", null),
	/**
	 * 
	 */
	GA(8, "Ga", "male_b", 4, 13, "pw", null),
	/**
	 * 
	 */
	ANDY(9, "Andy", "male_b", 4, 13, "pw", null),
	/**
	 * 
	 */
	DAVE(10, "Dave", "male_b", 4, 13, "pw", null);

	private int playerID;

	private String appearanceType;

	private int row;

	private int col;

	private String playerName;
	
	private String password;
	
	private String mapName;

	PlayersInDB(int id, String playerName, String type, int row, int col, String password, String mapName)
	{
		this.playerID = id;
		this.playerName = playerName;
		this.appearanceType = type;
		this.row = row;
		this.col = col;
		this.password = password;
		this.mapName = mapName;
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
	 * Get the name of the map the player was most recently on
	 * @return the map name
	 */
	public String getMapName()
	{
		return mapName;
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
	 * Get the player's unique name
	 * 
	 * @return the name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * 
	 * @return the player's password
	 */
	public String getPlayerPassword() 
	{
		return password;
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