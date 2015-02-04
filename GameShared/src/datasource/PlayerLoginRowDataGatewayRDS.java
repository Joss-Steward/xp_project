package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.DatabaseManager;

/**
 * The RDS implementation of the row data gateway
 * 
 * @author Merlin
 *
 */
public class PlayerLoginRowDataGatewayRDS implements PlayerLoginRowDataGateway
{

	private int playerID;
	private String playerName;
	private String password;
	private Connection connection;

	/**
	 * Drop and re-create the PlayerLogin table this gateway manages
	 * @throws DatabaseException if we can't talk to the RDS
	 */
	public static void createPlayerLoginTable() throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			Statement stmt = connection.createStatement();

			stmt.executeUpdate("DROP TABLE IF EXISTS PlayerLogins");
			StringBuffer sql = new StringBuffer("CREATE TABLE PlayerLogins(");
			sql.append("playerID int NOT NULL AUTO_INCREMENT, ");
			sql.append("playerName VARCHAR(30) NOT NULL,");
			sql.append("password VARCHAR(30) NOT NULL,");

			sql.append("PRIMARY KEY (PlayerID));");
			System.out.println(sql);
			stmt.executeUpdate(new String(sql));
			stmt.executeUpdate("ALTER TABLE PlayerLogins ENGINE = INNODB");
			stmt.executeUpdate("ALTER TABLE PlayerLogins ADD UNIQUE (PlayerName)");
			stmt.executeUpdate("ALTER TABLE PlayerLogins ADD UNIQUE (PlayerID)");

			stmt.close();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to initialize Player Login table", e);
		}

	}

	/**
	 * Finder constructor: will initialize itself by finding the appropriate row
	 * in the table
	 * 
	 * @param playerName
	 *            the player we are responsible for
	 * @throws DatabaseException
	 *             if we cannot find the given player in the table
	 */
	public PlayerLoginRowDataGatewayRDS(String playerName) throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		this.playerName = playerName;
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM PlayerLogins WHERE playerName = ?");
			stmt.setString(1, playerName);
			ResultSet result = stmt.executeQuery();
			result.next();
			password = result.getString("password");
			playerID = result.getInt("playerID");

		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a player named " + playerName, e);
		}
	}

	/**
	 * Create constructor: will create a new row in the table with the given
	 * information
	 * 
	 * @param playerName
	 *            the player's name
	 * @param password
	 *            the player's password
	 * @throws DatabaseException
	 *             if we can't create the player (can't connect or duplicate
	 *             name)
	 */
	public PlayerLoginRowDataGatewayRDS(String playerName, String password)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection.prepareStatement(
					"Insert INTO PlayerLogins SET playerName = ?, password = ?",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, playerName);
			stmt.setString(2, password);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
			{
				playerID = rs.getInt(1);
			}

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't create a player record for player named " + playerName, e);
		}
	}

	/**
	 * Finder constructor
	 * 
	 * @param playerID
	 *            the player's unique ID
	 * @throws DatabaseException
	 *             if the player isn't in the database or we can't execute the
	 *             query
	 */
	public PlayerLoginRowDataGatewayRDS(int playerID) throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		this.playerID = playerID;
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM PlayerLogins WHERE playerID = ?");
			stmt.setInt(1, playerID);
			ResultSet result = stmt.executeQuery();
			result.next();
			password = result.getString("password");
			playerName = result.getString("playerName");

		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find a player with ID " + playerID, e);
		}
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPassword()
	 */
	@Override
	public String getPassword()
	{
		return password;
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPlayerName()
	 */
	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();

		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("UPDATE PlayerLogins SET password = ? WHERE playerID = ?");
			stmt.setString(1, password);
			stmt.setInt(2, playerID);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't persist info for player named "
					+ playerName, e);
		}
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password)
	{
		this.password = password;

	}

}
