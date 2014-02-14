package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Merlin
 * 
 */
public class PlayerLogin
{

	private Connection connection;
	private int playerID;

	/**
	 * Create an object if the name and password are found in the db
	 * 
	 * @param name
	 *            the player's name
	 * @param password
	 *            the player's password
	 * @throws DatabaseException
	 *             if the name/password combination isn't found in the db
	 */
	public PlayerLogin(String name, String password) throws DatabaseException
	{
		try
		{
			connection = DatabaseManager.getSingleton().getConnection();
			String sql = "SELECT PlayerID FROM PlayerLogins WHERE PlayerName = ? and Password = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, password);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.first();
			playerID = resultSet.getInt(1);
			resultSet.close();
		} catch (SQLException e)
		{
			throw new DatabaseException("Could not retrieve Player with playername =  "
					+ name + " and password = " + password, e);
		}
	}

	/**
	 * Return this player's unique ID
	 * 
	 * @return the id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Generate the PIN this player should use for logging into his area server and put it in the DB
	 * @return the PIN
	 * @throws DatabaseException shouldn't
	 */
	public double generatePin() throws DatabaseException
	{
		double pin = Math.random();
		Connection connectionStatus = DatabaseManager.getSingleton().getConnection();
		try{
			String sql = "DELETE from PlayerPins WHERE PlayerID = ?";
			PreparedStatement stmt = connectionStatus.prepareStatement(sql);
			stmt.setInt(1, playerID);
			stmt.executeUpdate();
	
			sql = "INSERT INTO PlayerPins (PlayerID, Pin) VALUES (?, ?)";
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setInt(1, playerID);
			stmt.setDouble(2, pin);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			new DatabaseException("Unable to generate pin for player id # " + playerID, e);
		}
		return pin;
	}

}
