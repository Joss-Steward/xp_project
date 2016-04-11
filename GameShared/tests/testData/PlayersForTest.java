package testData;

import data.Crew;
import data.Major;
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
	JOHN(1, "John", "null_pointer_exception", 0, 8, "pw", "current.tmx", 1111, null, 42, 46, Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING),
	/**
	 * Merlin must be player 2 for the player mapper tests to pass. That number
	 * maps to player 2 in the quest and adventure states in QuestStatesForTest
	 * and AdventureStatesForTest
	 */
	MERLIN(2, "Merlin", "merlin", 4, 13, "pw", "current.tmx", 1111, null, 42, 46, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING),
	/**
	 * 
	 */
	NICK(3, "Nick", "off_by_one", 4, 13, "pw", "homework.tmx", 1111, null, 0, 35, Crew.OUT_OF_BOUNDS, Major.COMPUTER_SCIENCE),
	/**
	 * 
	 */
	JOSH(4, "Josh", "Ninja", 4, 13, "pw", "current.tmx", 1111, null, 0, 25, Crew.OUT_OF_BOUNDS, Major.SOFTWARE_ENGINEERING),
	/**
	 * 
	 */
	MATT(5, "Matt", "male_a", 4, 13, "pw", "current.tmx", 1111, null, 0, 12, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING),
	/**
	 * 
	 */
	STEVE(6, "Steve", "knight_with_straw_hat", 4, 13, "pw", "current.tmx", 1111, null, 0,
			13, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING),
	/**
	 * 
	 */
	FRANK(7, "Frank", "out_of_bounds", 4, 13, "pw", "current.tmx", 1441, null, 0, 13, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING),
	/**
	 * 
	 */
	GA(8, "Ga", "off_by_one", 4, 13, "pw", "current.tmx", 1111, null, 0, 15, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING),
	/**
	 * 
	 */
	ANDY(9, "Andy", "null_pointer_exception", 4, 13, "pw", "current.tmx", 1111, null, 0, 33, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING),
	/**
	 * 
	 */
	DAVE(10, "Dave", "out_of_bounds", 4, 13, "pw", null, 1111, null, 0, 12, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING),

	/**
	 * 
	 */
	LOSER(11, "Loser", "off_by_one", 4, 13, "pw", null, 1111, null, 0, 3, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING),

	/**
	 * 
	 */
	MOCK_NPC(12, "NPC1", "Magi", 4, 13, "", "silly.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING),

	/**
	 * Necessary for the game
	 */
	QUIZBOT(13, "QuizBot", "Magi", 4, 19, "", "quiznasium.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.ELECTRICAL_ENGINEERING),

	/**
	 * 
	 */
	MOCK_NPC3(14, "NPC3", "Magi", 4, 13, "", "silly.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING),

	/**
	 * 
	 */
	RYAN(15, "Ryan", "female_a", 4, 13, "pw", "current.tmx", 1111, null, 0, 13, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING),

	/**
	 * 
	 */
	NEWBIE(16, "Newbie", "male_a", 11, 7, "pw", "sortingRoom.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING),
	
	/**
 	 * 
	 */
	RED_HAT(17, "Red Hat", "RedHat", 9,7, "", "sortingRoom.tmx", 1111, null, 0, 0, Crew.OUT_OF_BOUNDS, Major.COMPUTER_ENGINEERING),
	
	/**
	 * 
	 */
	MARTY(18, "Marty", "off_by_one", 10, 19, "pw", "current.tmx", 1111, null, 0, 0, Crew.OFF_BY_ONE, Major.SOFTWARE_ENGINEERING),
	
	/**
     * 
     */
    HERSH(19, "Hersh", "null_pointer_exception", 4, 13, "pw", "current.tmx", 1111, null, 0, 0, Crew.NULL_POINTER, Major.SOFTWARE_ENGINEERING);

	private int playerID;
	private String appearanceType;
	private int row;
	private int col;
	private String playerName;
	private String password;
	private String mapName;
	private int pin;
	private String changedOn;
	private int knowledgePoints;
	private Crew crew;
	private Major major;

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

	PlayersForTest(int id, String playerName, String type, int row, int col,
			String password, String mapName, int pin, String changedOn, int quizScore,
			int experiencePoints, Crew crew, Major major)
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
		this.crew = crew;
		this.knowledgePoints = quizScore;
		this.major = major;
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
	 * 
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
	public int getKnowledgeScore()
	{
		return knowledgePoints;
	}

	/**
	 * @return this player's experience points
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	/**
	 * @return the crew to which this player belongs
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * @return the major for this student
	 */
	public Major getMajor()
	{
		return major;
	}


}
