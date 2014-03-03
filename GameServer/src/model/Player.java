package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import data.Position;
import model.reports.PlayerMovedReport;
import model.reports.QuestScreenReport;

/**
 * Very simple for now . . .
 * 
 * @author Merlin
 * 
 */
public class Player extends QualifiedObservable
{

	private int playerID;
	private String playerName;
	private Position playerPosition;

	/**
	 * Create a player without checking the pin (for testing purposes only)
	 * @param playerID
	 *            the unique ID of this player
	 * @throws DatabaseException 
	 */
	protected Player(int playerID) throws DatabaseException
	{
		this.playerID = playerID;
		this.playerName = readPlayerName();
		this.playerPosition = readPlayerPosition();
		reportTypes.add(PlayerMovedReport.class);
		reportTypes.add(QuestScreenReport.class);
		
		this.registerReportTypesWeNotify();
		// this.setQuestManager(new QuestManager());
	}

	/**
	 * Create a player that is connecting to this area server.  Check the pin to make sure it is correct
	 * @param playerID
	 *            the unique player id of this player
	 * @param pin
	 *            the pin we gave the player to connect to this area server
	 * @throws DatabaseException  shouldn't
	 */
	public Player(int playerID, double pin) throws DatabaseException
	{
		this(playerID);
		checkThePin(pin);
	}
	
	private void checkThePin(double pin) throws DatabaseException
	{
		PlayerPin pl = new PlayerPin(playerID);
		if(pin!= pl.retrievePin())
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
	 * @return the playerID of this player
	 */
	public int getPlayerID()
	{
		return playerID;
	}
	
	/**
	 * Get the unique player name of this player
	 * @return the player's name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * Set the player's position
	 * @param playerPosition
	 * 			The new location the player is
	 * Assuming a valid position.  Error checking else where
	 */
	public void setPlayerPosition(Position playerPosition)
	{
		this.playerPosition = playerPosition;
	}
	
	/**
	 * Get the player's position
	 * @return playerPosition
	 * 			Returns the player position. If a position is not set should return null.
	 */
	public Position getPlayerPosition()
	{
		return this.playerPosition;
	}
	
	
	/**
	 * Get this player's player name from the database
	 * 
	 * @return the players player name
	 * @throws DatabaseException if the player isn't found
	 */
	private String readPlayerName() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		String playerName;
		try
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT PlayerName from Players.PlayerLogins where PlayerID = ?");
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.first();
			playerName = resultSet.getString(1);
			resultSet.close();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to retrieve player with id = " + playerID, e);
		}
		return playerName;
	}

	/**
	 * Get the appearance type for how this player should be drawn
	 * @return a string matching one of the enum names in the PlayerType enum
	 * @throws DatabaseException shouldn't
	 */
	public String getAppearanceType() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		String playerName;
		try
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT AppearanceType from Players.Player where PlayerID = ?");
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.first();
			playerName = resultSet.getString(1);
			resultSet.close();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to retrieve appearance for player with id = " + playerID, e);
		}
		return playerName;
	}

	/**
	 * Get this player's position from the database
	 * 
	 * @return the players position
	 * @throws DatabaseException if the player isn't found
	 */
	private Position readPlayerPosition() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		Position position = null;
		try
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT Row, Col from Players.Player where PlayerID = ?");
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.first();
			position = new Position(resultSet.getInt(1), resultSet.getInt(2));
			resultSet.close();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to retrieve player with id = " + playerID, e);
		}
		return position;
	}
}
