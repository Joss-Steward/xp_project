package model;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Merlin
 * 
 */
@DatabaseTable(tableName = "PlayerLogins")
public class PlayerLogin
{

	private static final String PLAYERNAME_FIELD_NAME = "PlayerName";
	@DatabaseField
	private int playerID;
	@DatabaseField(id = true, columnName = PLAYERNAME_FIELD_NAME)
	private String playerName;
	@DatabaseField
	private String password;
	private static JdbcConnectionSource connectionSource;
	private static Dao<PlayerLogin, ?> playerLoginDao;

	/**
	 * Create a new record in the database
	 * 
	 * @param name
	 *            the player's name
	 * @param password
	 *            the player's password
	 */
	public static void createNewPlayerLogin(String name, String password)
	{
		try
		{
			setUpORMLite();
			PlayerLogin pl = new PlayerLogin();
			pl.playerName = name;
			pl.password = password;
			playerLoginDao.create(pl);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create an object if the name and password are found in the db
	 * 
	 * @param name
	 *            the player's name
	 * @param password
	 *            the player's password
	 * @return a player login from the given information only if the password is
	 *         correct
	 * @throws DatabaseException
	 *             if the name/password combination isn't found in the db
	 */
	public static PlayerLogin readAndVerifyPlayerLogin(String name, String password)
			throws DatabaseException
	{
		try
		{
			setUpORMLite();

			List<PlayerLogin> list;

			list = playerLoginDao.queryBuilder().where()
					.eq(PlayerLogin.PLAYERNAME_FIELD_NAME, name).query();
			if (list.size() == 1)
			{
				PlayerLogin pl = list.get(0);
				if (pl.password.equals(password))
				{
					return pl;
				}
			} else if (list.size() == 0)
			{
				throw new DatabaseException("no login information for " + name);
			} else
			{
				throw new DatabaseException("more than one login record for " + name);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Error retrieving login information for " + name);
		}

		throw new DatabaseException("incorrect password");
	}

	/**
	 * Get a player's login information without checking his password
	 * @param name the player's player name
	 * @return the player's login information
	 * @throws DatabaseException if the player doesn't exist, or has two records
	 */
	public static PlayerLogin readPlayerLogin(String name) throws DatabaseException
	{
		try
		{
			setUpORMLite();

			List<PlayerLogin> list;

			list = playerLoginDao.queryBuilder().where()
					.eq(PlayerLogin.PLAYERNAME_FIELD_NAME, name).query();
			if (list.size() == 1)
			{
				return list.get(0);
			} else if (list.size() == 0)
			{
				throw new DatabaseException("no login information for " + name);
			} else
			{
				throw new DatabaseException("more than one login record for " + name);
			}
		} catch (SQLException e)
		{
			throw new DatabaseException("Error retrieving login information for " + name);
		}
	}

	private static void setUpORMLite() throws SQLException
	{
		if (connectionSource == null)
		{
			String databaseUrl = "jdbc:mysql://shipsim.cbzhjl6tpflt.us-east-1.rds.amazonaws.com:3306/Players";
			connectionSource = new JdbcConnectionSource(databaseUrl, "program", "ShipSim");
			playerLoginDao = DaoManager.createDao(connectionSource, PlayerLogin.class);
		}
	}

	/**
	 * No arg constructor for ORMLite
	 */
	public PlayerLogin()
	{
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
	 * Get the player's playername
	 * @return the playername
	 */
	public String getPlayerName()
	{
		return playerName;
	}

}
