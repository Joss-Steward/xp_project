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
	 * John must be player 1 for the current quest state report tests to pass
	 */
	JOHN(1, "John", "male_a", 0, 8, "pw", "quiznasium.tmx", 1111, null, 0, 15),
	/**
	 *  Merlin must be player 2 for the player mapper tests to pass.  That number maps to player 2
	 *  in the quest and adventure states in QuestStatesForTest and AdventureStatesForTest
	 */
	MERLIN(2, "Merlin", "merlin", 4, 13, "pw", "current.tmx", 1111, null, 42, 46),
	/**
	 * 
	 */
	NICK(3, "Nick", "male_b", 4, 13, "pw", "current.tmx", 1111, null, 0, 35),
	/**
	 * 
	 */
	JOSH(4, "Josh", "Ninja", 4, 13, "pw", "current.tmx", 1111, null, 0, 25),
	/**
	 * 
	 */
	MATT(5, "Matt", "male_b", 4, 13, "pw", "current.tmx", 1111, null, 0, 12),
	/**
	 * 
	 */
	STEVE(6, "Steve", "knight_with_straw_hat", 4, 13, "pw", "current.tmx", 1111, null, 0, 13),
	/**
	 * 
	 */
	FRANK(7, "Frank", "male_c", 4, 13, "pw", "current.tmx", 1441, null, 0, 13),
	/**
	 * 
	 */
	GA(8, "Ga", "male_b", 4, 13, "pw", "current.tmx", 1111, null, 0, 15),
	/**
	 * 
	 */
	ANDY(9, "Andy", "male_b", 4, 13, "pw", null, 1111, null, 0, 33),
	/**
	 * 
	 */
	DAVE(10, "Dave", "male_b", 4, 13, "pw", null, 1111, null, 0, 12),
	
	/**
	 * 
	 */
	LOSER(11, "Loser","male_b", 4, 13, "pw", null, 1111, null, 0, 3),
	
	/**
	 * 
	 */
	MOCK_NPC(12, "NPC1","Magi", 4, 13, "", "silly.tmx", 1111, null, 0, 0),
	
	/**
	 * 
	 */
	QUIZBOT(13, "QuizBot","Magi", 4, 13, "", "quiznasium.tmx", 1111, null, 0, 0),
	
	/**
	 * 
	 */
	MOCK_NPC3(14, "NPC3","Magi", 4, 13, "", "silly.tmx", 1111, null, 0, 0),
	
	/**
	 * 
	 */
	RYAN(15, "Ryan", "female_a", 4, 13, "pw", "current.tmx", 1111, null, 0, 13);
	
	
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


	
	private int experiencePoints;

	PlayersForTest(int id, String playerName, String type, int row, int col, String password, String mapName, int pin, String changedOn, int quizScore, int experiencePoints)
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
		this.experiencePoints = experiencePoints;
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

	/**
	 * @return this player's experience points
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

}