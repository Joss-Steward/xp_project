package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import model.reports.PlayerMovedReport;
import model.reports.QuestScreenReport;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
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
	private int playerID;
	@DatabaseField
	private String playerName;
	@DatabaseField
	private String appearanceType;

	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Position playerPosition;

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
		PlayerPin pl = new PlayerPin(playerID);
		if (pin != pl.retrievePin())
		{
			throw new DatabaseException("Wrong PIN for player #" + playerID);
		}
		GregorianCalendar now = new GregorianCalendar();
		now.setTimeZone(TimeZone.getTimeZone("GMT"));
		if (pl.getExpirationTime().before(now))
		{
			throw new DatabaseException("Expired PIN for player #" + playerID);
		}
	}

	/**
	 * Get the appearance type for how this player should be drawn
	 * 
	 * @return a string matching one of the enum names in the PlayerType enum
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public String getAppearanceType() throws DatabaseException
	{
		return appearanceType;
	}

	/**
	 * @return the playerID of this player
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
		return playerName;
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

	public void setAppearanceType(String appearanceType)
	{
		this.appearanceType = appearanceType;
	}

	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
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
		this.playerPosition = playerPosition;
		PlayerMovedReport report = new PlayerMovedReport(this.playerID, this.playerName,
				playerPosition);
		this.notifyObservers(report);
	}
}
