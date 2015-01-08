package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.DatabaseException;
import model.DatabaseManager;

/**
 * The RDS implementation of the row data gateway
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
	 * @see datasource.PlayerLoginRowDataGateway#create(java.lang.String, java.lang.String)
	 */
	@Override
	public int create(String playerName, String password) throws DatabaseException
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
					"Couldn't create a s player record for player named " + playerName, e);
		}
		return playerID;
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#find(java.lang.String)
	 */
	@Override
	public void find(String playerName) throws DatabaseException
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
	 * @see datasource.PlayerLoginRowDataGateway#getPassword()
	 */
	@Override
	public String getPassword()
	{
		return password;
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password)
	{
		this.password = password;

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
	 * @see datasource.PlayerLoginRowDataGateway#getPlayerName()
	 */
	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

}
