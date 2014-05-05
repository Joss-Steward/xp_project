package model;

import java.util.GregorianCalendar;
import java.util.TimeZone;

import model.reports.PlayerMovedReport;
import model.reports.QuestScreenReport;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import data.Position;

/**
 * Very simple for now . . .
 * 
 * @author Merlin
 * 
 */
@DatabaseTable(tableName = "Player")
public class Player extends QualifiedObservable
{

	@DatabaseField(id = true)
	private int id;
	
	@DatabaseField(foreign = true)
	private PlayerLogin playerLogin;
	
	@DatabaseField
	private String appearanceType;
	
	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Position playerPosition;
	
	@DatabaseField
	private int quizScore;

	/**
	 * No arg constructor for ORMLite
	 */
	public Player()
	{
		registerOurReportTypes();
	}

	/**
	 * Check the pin to make sure it is correct
	 * 
	 * @param pin
	 *            the pin we gave the player to connect to this area server
	 * @throws DatabaseException
	 *             shouldn't
	 */

	public void checkThePin(double pin) throws DatabaseException
	{
		PlayerPin pl = new PlayerPin(id);
		if (pin != pl.retrievePin())
		{
			throw new DatabaseException("Wrong PIN for player #" + id);
		}
		GregorianCalendar now = new GregorianCalendar();
		now.setTimeZone(TimeZone.getTimeZone("GMT"));
		if (pl.getExpirationTime().before(now))
		{
			throw new DatabaseException("Expired PIN for player #" + id);
		}
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
		return id;
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
		return this.playerPosition;
	}

	private void registerOurReportTypes()
	{
		reportTypes.add(PlayerMovedReport.class);
		reportTypes.add(QuestScreenReport.class);

		this.registerReportTypesWeNotify();
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
	 * Set the player's position
	 * 
	 * @param playerPosition
	 *            The new location the player is Assuming a valid position.
	 *            Error checking else where
	 */
	public void setPlayerPosition(Position playerPosition)
	{
		setPlayerPositionWithoutNotifying(playerPosition);
		PlayerMovedReport report = new PlayerMovedReport(this.id, this.getPlayerName(),
				playerPosition);
		this.notifyObservers(report);
	}

	/**
	 * Tell this player what his login information is
	 * @param pl his login information
	 */
	public void setPlayerLogin(PlayerLogin pl)
	{
		this.playerLogin = pl;
	}

	/**
	 * Move a player without notifying the other players (used when moving from one map to another
	 * @param newPosition the position the player should move to
	 */
	public void setPlayerPositionWithoutNotifying(Position newPosition)
	{
		this.playerPosition = newPosition;
	}

	/**
	 * Set the id
	 * @param id the new id
	 */
	public void setId(int id) 
	{
		this.id = id;
	}
	
	/**
	 * Get the quizScore
	 * @return
	 * 			the quiz score
	 */
	public int getQuizScore()
	{
		return this.quizScore;
	}
	
	/**
	 * Set the quizScore
	 * @param score
	 * 			the new quiz score
	 */
	public void setQuizScore(int score)
	{
		this.quizScore = score;
	}
	
	/**
	 * Increment quiz score;
	 */
	public void incrementQuizScore()
	{
		this.quizScore++;
	}
}
