package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	/**
	 * Create a player without checking the pin (for testing purposes only)
	 * @param userID
	 *            the userid of this player
	 * @throws DatabaseException 
	 */
	protected Player(int userID) throws DatabaseException
	{
		this.playerID = userID;
		this.playerName = readUserName();
		QualifiedObservableConnector.getSingleton()
				.registerQualifiedObservable(this, PlayerMovedReport.class);
		// this.setQuestManager(new QuestManager());

		QualifiedObservableConnector.getSingleton()
				.registerQualifiedObservable(this, QuestScreenReport.class);
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
	 * @return the userID of this player
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
	 * @see model.QualifiedObservable#notifiesOn(java.lang.Class)
	 */
	@Override
	public boolean notifiesOn(Class<? extends QualifiedObservableReport> reportType)
	{
		if (reportType.equals(PlayerMovedReport.class))
		{
			return true;
		}
		return false;
	}

	/**
	 * Get this player's user name from the database
	 * 
	 * @return the players user name
	 * @throws DatabaseException if the player isn't found
	 */
	private String readUserName() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		String userName;
		try
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT PlayerName from Players.PlayerLogins where PlayerID = ?");
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.first();
			userName = resultSet.getString(1);
			resultSet.close();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to retrieve player with id = " + playerID, e);
		}
		return userName;
	}

}
