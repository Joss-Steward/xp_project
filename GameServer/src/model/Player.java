package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		checkThePin();
	}
	
	private void checkThePin()
	{
		// TODO need to check their pin when they are connecting
		
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

}
