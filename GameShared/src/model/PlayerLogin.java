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

}
