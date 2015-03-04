package edu.ship.shipsim.areaserver.model;

import java.util.ArrayList;

import model.PlayerConnection;
import model.PlayerLogin;
import model.QualifiedObservable;
import data.Position;
import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.model.reports.PinFailedReport;
import edu.ship.shipsim.areaserver.model.reports.PlayerMovedReport;

/**
 * Very simple for now . . .
 * 
 * @author Merlin
 * 
 */
public class Player extends QualifiedObservable
{

	private PlayerLogin playerLogin;

	private int quizScore;

	private PlayerMapper playerMapper;

	private String appearanceType;

	private int playerID;

	private Position playerPosition;

	private String mapName;
	
	private ArrayList<QuestState> questList = new ArrayList<QuestState>();

	/**
	 * 
	 */
	Player()
	{
		registerOurReportTypes();
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
	 * @return the id of this player
	 */
	public int getID()
	{
		return playerID;
	}

	/**
	 * @return the name of the map this player is on
	 */
	public String getMapName()
	{
		return mapName;
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
		return this.quizScore;
	}

	/**
	 * Increment quiz score;
	 */
	public void incrementQuizScore()
	{
		this.quizScore++;
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
		PinFailedReport report = null;

		if (!pl.isPinValid(pinToCheck))
		{
			report = new PinFailedReport(PlayerConnection.ERROR_PIN_NOT_EXIST);
		} else if (pl.isExpired())
		{
			report = new PinFailedReport(PlayerConnection.ERROR_PIN_EXPIRED);
		}

		if (report != null)
		{
			System.err.println("Pin is not valid for " + playerID
					+ " because " + report.toString());
			this.notifyObservers(report);
			return false;
		}
		return true;
	}

	/**
	 * store the information into the data source
	 * 
	 * @throws DatabaseException if the data source fails to complete the persistance
	 */
	public void persist() throws DatabaseException
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
	 * @param playerMapper the mapper that will be used to persist this player
	 */
	public void setDataMapper(PlayerMapper playerMapper)
	{
		this.playerMapper = playerMapper;
	}

	/**
	 * @param mapName the name of the map this player should be on
	 */
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}

	/**
	 * @param playerID this player's unique ID
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
		PlayerMovedReport report = new PlayerMovedReport(playerID,
				this.getPlayerName(), playerPosition);
		this.notifyObservers(report);
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
		this.quizScore = score;
	}

	/**
	 * Add a quest to the player's questList
	 * @param quest : the quest being added
	 */
	public void addQuestState(QuestState quest) 
	{
		questList.add(quest);
	}

	/**
	 * Go through the questList and get the state of the quest based on the id
	 * @param id : the id of the quest
	 * @return the state of the quest
	 */
	public QuestState getQuestStateByID(int id) 
	{
		for(QuestState q : questList)
		{
			if(q.getID()==id)
			{
				return q;
			}
		}
		return null;
	}

	/**
	 * Return the size of the questList
	 * @return questList size
	 */
	public int getSizeOfQuestList() 
	{
		return questList.size();
	}
	
	private void registerOurReportTypes()
	{
		reportTypes.add(PlayerMovedReport.class);
		reportTypes.add(PinFailedReport.class);

		this.registerReportTypesWeNotify();
	}
}
