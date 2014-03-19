package model;

import java.sql.SQLException;
import java.util.HashMap;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import model.reports.PlayerConnectionReport;

/**
 * @author Merlin
 * 
 */
public class PlayerManager extends QualifiedObservable
{

	private static PlayerManager singleton;

	/**
	 * @return the only PlayerManger in the system
	 */
	public synchronized static PlayerManager getSingleton()
	{
		if (singleton == null)
		{
			try
			{
				singleton = new PlayerManager();
			} catch (DatabaseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return singleton;
	}

	/**
	 * reset the singleton for testing purposes.
	 */
	public static void resetSingleton()
	{
		if (singleton != null)
		{
			for (Class<?> reportType : singleton.reportTypes)
			{
				QualifiedObservableConnector.getSingleton()
						.unregisterQualifiedObservable(singleton, reportType);
			}
			singleton = null;
		}
	}

	private HashMap<Integer, Player> players;
	private JdbcConnectionSource connectionSource;

	private Dao<Player, Integer> playerDao;

	/**
	 * Get the connection source ormlite will use (only for testing)
	 * 
	 * @return the connection source
	 */
	public JdbcConnectionSource getConnectionSource()
	{
		return connectionSource;
	}

	/**
	 * Get the player Data Access Object (only for testing
	 * 
	 * @return the DAO from ormlite
	 */
	public Dao<Player, Integer> getPlayerDao()
	{
		return playerDao;
	}

	private void setUpDAOObject() throws SQLException
	{
		String databaseUrl = "jdbc:mysql://shipsim.cbzhjl6tpflt.us-east-1.rds.amazonaws.com:3306/Players";
		connectionSource = new JdbcConnectionSource(databaseUrl, "program", "ShipSim");
		playerDao = DaoManager.createDao(connectionSource, Player.class);
	}

	private PlayerManager() throws DatabaseException
	{
		try
		{
			setUpDAOObject();
		} catch (SQLException e)
		{
			throw new DatabaseException("Unable to set up Player DAO");
		}
		players = new HashMap<Integer, Player>();
		reportTypes.add(PlayerConnectionReport.class);

		this.registerReportTypesWeNotify();
	}

	/**
	 * @return the number of players currently on this system
	 */
	public int numberOfPlayers()
	{
		return players.size();
	}

	/**
	 * Adds a player to the list of active players on this server
	 * 
	 * @param playerID
	 *            the players id number
	 * @param pin
	 *            the pin we gave the client to connect to this server
	 * @return the player object that we added
	 * @throws DatabaseException
	 *             if the player's pin was not correct
	 */
	public Player addPlayer(int playerID, double pin) throws DatabaseException
	{
		try
		{
			Player player = playerDao.queryForId(playerID);
			player.checkThePin(pin);
			players.put(playerID, player);

			this.notifyObservers(new PlayerConnectionReport(player));
			return player;
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Adds a player to the list of active players on this server without
	 * checking its pin - only for testing purposes
	 * 
	 * @param playerID
	 *            the players id number
	 * @return the player object for the added player
	 */
	public Player addPlayer(int playerID)
	{
		try
		{
			Player player = playerDao.queryForId(playerID);
			players.put(playerID, player);

			this.notifyObservers(new PlayerConnectionReport(player));
			return player;
		} catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param playerID
	 *            the playerID of the player we are looking for
	 * @return the player we were looking for
	 */
	public Player getPlayerFromID(int playerID)
	{
		return players.get(playerID);
	}

	/**
	 * @param playerName
	 *            the player name of the player we are searching for
	 * @return the player ID of the player we are searching for
	 * @throws PlayerNotFoundException
	 *             if no player with that player name is found
	 */
	public int getPlayerIDFromPlayerName(String playerName)
			throws PlayerNotFoundException
	{
		java.util.Iterator<Player> i = players.values().iterator();
		while (i.hasNext())
		{
			Player p = i.next();
			if (p.getPlayerName().equals(playerName))
			{
				return p.getPlayerID();
			}
		}
		throw new PlayerNotFoundException();
	}
}
