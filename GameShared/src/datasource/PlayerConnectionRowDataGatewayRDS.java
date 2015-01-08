package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseException;
import model.DatabaseManager;

/**
 * AN RDS Implementation of PlayerConnectionRowDataGateway
 * @author Merlin
 *
 */
public class PlayerConnectionRowDataGatewayRDS implements PlayerConnectionRowDataGateway
{

	private int playerID;
	private int pin;
	private String changedOn;
	private String mapName;

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#findPlayer(int)
	 */
	@Override
	public void findPlayer(int playerID) throws DatabaseException
	{
		this.playerID = playerID;
		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();
			String sql = "SELECT * FROM PlayerConnection WHERE PlayerID = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.next();
			pin = resultSet.getInt("Pin");
			changedOn = resultSet.getString("changed_on");
			mapName = resultSet.getString("MapName");

		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to get the data for a connection for "
					+ playerID);
		}
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#storePin(int)
	 */
	@Override
	public void storePin(int pin) throws DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton().getConnection();
		try
		{
			String sql;
			PreparedStatement stmt;
			deleteRow();

			sql = "INSERT INTO PlayerConnection (PlayerID, Pin) VALUES (?, ?)";
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setInt(1, playerID);
			stmt.setInt(2, pin);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			new DatabaseException("Unable to generate pin for player id # " + playerID, e);
		}
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#getPin()
	 */
	@Override
	public int getPin()
	{
		return pin;
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#getChangedOn()
	 */
	@Override
	public String getChangedOn() throws DatabaseException
	{
		return changedOn;
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#deleteRow()
	 */
	@Override
	public void deleteRow() throws DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton().getConnection();

		String sql = "DELETE from PlayerConnection WHERE PlayerID = ?";
		PreparedStatement stmt;
		try
		{
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setInt(1, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to delete the pin for player id # "
					+ playerID, e);
		}
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#create(int, int, java.lang.String)
	 */
	@Override
	public void create(int playerID, int pin, String mapName) throws DatabaseException
	{
		this.playerID = playerID;
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("Insert INTO PlayerConnection SET PlayerID = ?, Pin = ?, changed_on = ?, mapName = ?");
			stmt.setInt(1, playerID);
			stmt.setInt(2, pin);
			stmt.setString(3, changedOn);
			stmt.setString(4, mapName);
			stmt.executeUpdate();

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a player connection record for player id "
							+ playerID, e);
		}
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#getMapName()
	 */
	@Override
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#storeMapName(java.lang.String)
	 */
	@Override
	public void storeMapName(String mapFileTitle) throws DatabaseException
	{
		try
		{
			Connection connectionStatus = DatabaseManager.getSingleton().getConnection();

			String sql;
			PreparedStatement stmt;

			sql = "UPDATE PlayerConnection SET mapName=? WHERE PlayerID = ?";
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setString(1, mapFileTitle);
			stmt.setInt(2, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Unable to store map information for player id # " + playerID, e);
		}
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#setChangedOn(java.lang.String)
	 */
	@Override
	public void setChangedOn(String newTime) throws DatabaseException
	{
		Connection connectionStatus = DatabaseManager.getSingleton().getConnection();
		try
		{
			String sql;
			PreparedStatement stmt;

			sql = "UPDATE PlayerConnection SET changed_On=? WHERE PlayerID = ?";
			stmt = connectionStatus.prepareStatement(sql);
			stmt.setString(1, newTime);
			stmt.setInt(2, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to generate pin for player id # "
					+ playerID, e);
		}
	}

}
