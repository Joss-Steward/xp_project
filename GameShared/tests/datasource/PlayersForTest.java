package datasource;

import data.Position;

/**
 * The players that are in the test database
 * 
 * @author Merlin
 * 
 */
public enum PlayersForTest
{
	/**
	 * 
	 */
	JOHN(1, "John", "male_a", 0, 8, "pw", "quiznasium.tmx", 1111, null, "current.tmx", 0),
	/**
	 *  Merlin must be player 2 for the player mapper tests to pass.  That number maps to player 2
	 *  in the quest and adventure states in QuestStatesForTest and AdventureStatesForTest
	 */
	MERLIN(2, "Merlin", "merlin", 4, 13, "pw", null, 1111, null, "current.tmx", 42),
	/**
	 * 
	 */
	NICK(3, "Nick", "magi", 4, 13, "pw", "current.tmx", 1111, null, "current.tmx", 0),
	/**
	 * 
	 */
	JOSH(4, "Josh", "ninja", 4, 13, "pw", "current.tmx", 1111, null, "current.tmx", 0),
	/**
	 * 
	 */
	MATT(5, "Matt", "male_b", 4, 13, "pw", "current.tmx", 1111, null, "current.tmx", 0),
	/**
	 * 
	 */
	STEVE(6, "Steve", "knight_with_straw_hat", 4, 13, "pw", "current.tmx", 1111, null, "current.tmx", 0),
	/**
	 * 
	 */
	FRANK(7, "Frank", "male_b", 4, 13, "pw", "current.tmx", 1441, null, "current.tmx", 0),
	/**
	 * 
	 */
	GA(8, "Ga", "male_b", 4, 13, "pw", "current.tmx", 1111, null, "current.tmx", 0),
	/**
	 * 
	 */
	ANDY(9, "Andy", "male_b", 4, 13, "pw", null, 1111, null, "current.tmx", 0),
	/**
	 * 
	 */
	DAVE(10, "Dave", "male_b", 4, 13, "pw", null, 1111, null, "current.tmx", 0),
	
	/**
	 * 
	 */
	MOCK_NPC(11, "NPC1","magi", 4, 13, "", "silly.tmx", 1111, null, "silly.tmx", 0),
	
	/**
	 * 
	 */
	QUIZBOT(12, "QuizBot","magi", 4, 13, "", "quiznasium.tmx", 1111, null, "quiznasium.tmx", 0),
	
	/**
	 * 
	 */
	MOCK_NPC3(13, "NPC3","magi", 4, 13, "", "silly.tmx", 1111, null, "silly.tmx", 0);
	
	private int playerID;

	private String appearanceType;

	private int row;

	private int col;

	private String playerName;
	
	private String password;
	
	private String mapName;
	
	private int pin;
	
	private String changedOn;
	
	private int quizScore;
	
	/**
	 * @return the pin for the current connection
	 */
	public int getPin()
	{
		return pin;
	}

	/**
	 * @return the time when the pin for the current connection was set
	 */
	public String getChangedOn()
	{
		return changedOn;
	}

	/**
	 * @return the map name the pin for the current connection is good for
	 */
	public String getMapNameForPin()
	{
		return mapNameForPin;
	}

	private String mapNameForPin;

	PlayersForTest(int id, String playerName, String type, int row, int col, String password, String mapName, int pin, String changedOn, String mapNameForPin, int quizScore)
	{
		this.playerID = id;
		this.playerName = playerName;
		this.appearanceType = type;
		this.row = row;
		this.col = col;
		this.password = password;
		this.mapName = mapName;
		this.pin = pin;
		this.changedOn = changedOn;
		this.mapNameForPin = mapNameForPin;
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

	/**
	 * get this player's current quiz score
	 * 
	 * @return this player's score
	 */
	public int getQuizScore()
	{
		return quizScore;
	}

}