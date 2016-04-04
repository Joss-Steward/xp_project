package model;

import model.reports.ExperienceChangedReport;
import model.reports.PlayerMovedReport;
import model.reports.KnowledgePointsChangeReport;
import data.Crew;
import data.Position;
import datasource.DatabaseException;

/**
 * Very simple for now . . .
 * 
 * @author Merlin
 * 
 */
public class Player
{

	private PlayerLogin playerLogin;

	private int knowledgePoints;

	private PlayerMapper playerMapper;

	private String appearanceType;

	private int playerID;

	private Position playerPosition;

	private String mapName;

	private int experiencePoints;

	private Crew crew;

	final private int LOCAL_CHAT_RADIUS = 5;

	/**
	 * Add experience points and generates ExperienceChangedReport
	 * 
	 * @param expPoints
	 *            Player's experience points
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public void addExperiencePoints(int expPoints) throws DatabaseException
	{
		this.experiencePoints = experiencePoints + expPoints;
		ExperienceChangedReport report = new ExperienceChangedReport(this.playerID,
				this.experiencePoints, LevelManager.getSingleton().getLevelForPoints(
						this.experiencePoints));
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * Get the appearance type for how this player should be drawn
	 * 
	 * @return a string matching one of the enum names in the PlayerType enum
	 */
	public String getAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @return the crew to which this player belongs
	 */
	public Crew getCrew()
	{
		return crew;
	}

	/**
	 * Get the player's experience points
	 * 
	 * @return experience points
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	/**
	 * @return the name of the map this player is on
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * @return the id of this player
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Get the unique player name of this player
	 * 
	 * @return the player's name
	 */
	public String getPlayerName()
	{
		return playerLogin.getPlayerName();
	}

	/**
	 * Get the player's position
	 * 
	 * @return playerPosition Returns the player position. If a position is not
	 *         set should return null.
	 */
	public Position getPlayerPosition()
	{
		return playerPosition;
	}

	/**
	 * Get the quizScore
	 * 
	 * @return the quiz score
	 */
	public int getQuizScore()
	{
		return this.knowledgePoints;
	}

	/**
	 * Increment quiz score;
	 */
	public void incrementQuizScore()
	{
		this.knowledgePoints++;
		
		KnowledgePointsChangeReport report = new KnowledgePointsChangeReport(playerID, this.knowledgePoints);
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * Check the pin to make sure it is correct with regards to contents and
	 * expiration
	 * 
	 * @param pinToCheck
	 *            the pin we gave the player to connect to this area server
	 * @return true or false with pin validity
	 * @throws DatabaseException
	 *             if the data source had an exception
	 */

	public boolean isPinValid(double pinToCheck) throws DatabaseException
	{
		PlayerConnection pl = new PlayerConnection(playerID);
		if (!pl.isPinValid(pinToCheck) || pl.isExpired())
		{
			return false;
		}
		return true;
	}

	/**
	 * store the information into the data source
	 * 
	 * @throws DatabaseException
	 *             if the data source fails to complete the persistance
	 * @throws IllegalQuestChangeException
	 *             shouldn't
	 */
	public void persist() throws DatabaseException, IllegalQuestChangeException
	{
		playerMapper.persist();
	}

	/**
	 * Set the appearance type for this player
	 * 
	 * @param appearanceType
	 *            the new appearance type
	 */
	public void setAppearanceType(String appearanceType)
	{
		this.appearanceType = appearanceType;
	}

	/**
	 * @param crew
	 *            the crew to which this player should belong
	 */
	public void setCrew(Crew crew)
	{
		this.crew = crew;
	}

	/**
	 * @param playerMapper
	 *            the mapper that will be used to persist this player
	 */
	public void setDataMapper(PlayerMapper playerMapper)
	{
		this.playerMapper = playerMapper;
	}

	/**
	 * Set experience points and generates ExperienceChangedReport
	 * 
	 * @param expPoints
	 *            Player's experience points
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public void setExperiencePoints(int expPoints) throws DatabaseException
	{
		this.experiencePoints = expPoints;
	}

	/**
	 * @param mapName
	 *            the name of the map this player should be on
	 */
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}

	/**
	 * @param playerID
	 *            this player's unique ID
	 */
	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}

	/**
	 * Tell this player what his login information is
	 * 
	 * @param pl
	 *            his login information
	 */
	public void setPlayerLogin(PlayerLogin pl)
	{
		this.playerLogin = pl;
	}

	/**
	 * Set the player's position
	 * 
	 * @param playerPosition
	 *            The new location the player is Assuming a valid position.
	 *            Error checking else where
	 */
	public void setPlayerPosition(Position playerPosition)
	{
		setPlayerPositionWithoutNotifying(playerPosition);

		sendReportGivingPosition();

	}

	/**
	 * Move a player without notifying the other players (used when moving from
	 * one map to another
	 * 
	 * @param newPosition
	 *            the position the player should move to
	 */
	public void setPlayerPositionWithoutNotifying(Position newPosition)
	{
		this.playerPosition = newPosition;
	}

	/**
	 * Set the quizScore
	 * 
	 * @param score
	 *            the new quiz score
	 */
	public void setQuizScore(int score)
	{
		this.knowledgePoints = score;
	}

	/**
	 * When receiving a local message check if the player is close enough to
	 * hear
	 * 
	 * @param position
	 *            position of the sender
	 */
	protected boolean canReceiveLocalMessage(Position position)
	{
		Position myPosition = getPlayerPosition();

		return Math.abs(myPosition.getColumn() - position.getColumn()) <= LOCAL_CHAT_RADIUS
				&& Math.abs(myPosition.getRow() - position.getRow()) <= LOCAL_CHAT_RADIUS;
	}

	/**
	 * Report our position to anyone who is interested
	 */
	public void sendReportGivingPosition()
	{
		PlayerMovedReport report = new PlayerMovedReport(playerID, this.getPlayerName(),
				playerPosition, this.mapName);

		QualifiedObservableConnector.getSingleton().sendReport(report);
	}
	
	/**
	 * @return the number of knowledge points of this player
	 */
	public int getKnowledgePoints() 
	{
		return knowledgePoints;
	}
}
