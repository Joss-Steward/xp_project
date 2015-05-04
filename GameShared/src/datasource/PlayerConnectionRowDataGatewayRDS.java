package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AN RDS Implementation of PlayerConnectionRowDataGateway
 * 
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
	 * Drop and reinitialize the table this gateway manages
	 * @throws DatabaseException if we can't talk to the RDS
	 */
	public static void createPlayerConnectionTable() throws DatabaseException
	{

		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,"DROP TABLE IF EXISTS PlayerConnection");
			stmt.executeUpdate();
			StringBuffer sql = new StringBuffer("CREATE TABLE PlayerConnection(");
			sql.append("PlayerID int NOT NULL, ");
			sql.append("Pin double NOT NULL,");
			sql.append("changed_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
			sql.append("MapName VARCHAR(30),");

			sql.append("PRIMARY KEY (PlayerID));");
			System.out.println(sql);
			stmt.executeUpdate( sql.toString());
			stmt.executeUpdate("ALTER TABLE PlayerConnection ENGINE = INNODB");
			stmt.executeUpdate("ALTER TABLE PlayerConnection ADD UNIQUE (PlayerID)");
			stmt.close();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to create the PlayerConnection Table",e);
		}
		
	}
	/**
	 * Finder constructor
	 * 
	 * @param playerID
	 *            the player we are interested in
	 * @throws DatabaseException
	 *             if we cannot find that player in the db
	 */
	public PlayerConnectionRowDataGatewayRDS(int playerID) throws DatabaseException
	{
		this.playerID = playerID;
		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();
			String sql = "SELECT * FROM PlayerConnection WHERE PlayerID = ?";
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,sql);
			stmt.setInt(1, playerID);
			ResultSet resultSet = stmt.executeQuery();
			resultSet.next();
			pin = resultSet.getInt("Pin");
			changedOn = resultSet.getString("changed_on");
			mapName = resultSet.getString("MapName");

		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to get the data for a connection for "
					+ playerID,e);
		}
	}

	/**
	 * Creation constructor: will store the given information in the db
	 * 
	 * @param playerID
	 *            the player's unique id
	 * @param pin
	 *            the pin the player must send to connect
	 * @param mapName
	 *            the map of the area the player will connect to
	 * @throws DatabaseException
	 *             Either we can't connect to the db or the player ID is already
	 *             in the db
	 */
	public PlayerConnectionRowDataGatewayRDS(int playerID, int pin, String mapName)
			throws DatabaseException
	{
		this.playerID = playerID;
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"Insert INTO PlayerConnection SET PlayerID = ?, Pin = ?, changed_on = ?, mapName = ?");
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
	 * @see datasource.PlayerConnectionRowDataGateway#deleteRow()
	 */
	@Override
	public void deleteRow() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();

		String sql = "DELETE from PlayerConnection WHERE PlayerID = ?";
		ClosingPreparedStatement stmt;
		try
		{
			stmt = new ClosingPreparedStatement(connection,sql);
			stmt.setInt(1, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to delete the pin for player id # "
					+ playerID, e);
		}
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
	 * @see datasource.PlayerConnectionRowDataGateway#getMapName()
	 */
	@Override
	public String getMapName()
	{
		return mapName;
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
	 * @see datasource.PlayerConnectionRowDataGateway#setChangedOn(java.lang.String)
	 */
	@Override
	public void setChangedOn(String newTime) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			String sql;
			ClosingPreparedStatement stmt;

			sql = "UPDATE PlayerConnection SET changed_On=? WHERE PlayerID = ?";
			stmt = new ClosingPreparedStatement(connection,sql);
			stmt.setString(1, newTime);
			stmt.setInt(2, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to generate pin for player id # "
					+ playerID, e);
		}
	}

	/**
	 * @see datasource.PlayerConnectionRowDataGateway#storeMapName(java.lang.String)
	 */
	@Override
	public void storeMapName(String mapFileTitle) throws DatabaseException
	{
		try
		{
			Connection connection = DatabaseManager.getSingleton().getConnection();

			String sql;
			ClosingPreparedStatement stmt;

			sql = "UPDATE PlayerConnection SET mapName=? WHERE PlayerID = ?";
			stmt = new ClosingPreparedStatement(connection,sql);
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
	 * @see datasource.PlayerConnectionRowDataGateway#storePin(int)
	 */
	@Override
	public void storePin(int pin) throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			String sql;
			ClosingPreparedStatement stmt;
			deleteRow();

			sql = "INSERT INTO PlayerConnection (PlayerID, Pin) VALUES (?, ?)";
			stmt = new ClosingPreparedStatement(connection,sql);
			stmt.setInt(1, playerID);
			stmt.setInt(2, pin);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			new DatabaseException("Unable to generate pin for player id # " + playerID, e);
		}
	}

}
