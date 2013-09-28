package model;

import java.util.HashMap;

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
			singleton = new PlayerManager();
		}
		return singleton;
	}

	/**
	 * reset the singleton for testing purposes.
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}

	private HashMap<Integer,Player> players;

	private PlayerManager()
	{
		players = new HashMap<Integer,Player>();
		QualifiedObservableConnector.getSingleton().registerQualifiedObservable(this, PlayerConnectionReport.class);
	}

	/**
	 * @return the number of players currently on this system
	 */
	public int numberOfPlayers()
	{
		return players.size();
	}

	/**
	 * @see model.QualifiedObservable#notifiesOn(java.lang.Class)
	 */
	@Override
	public boolean notifiesOn(Class<?> reportType)
	{
		if (reportType.equals(PlayerConnectionReport.class))
		{
			return true;
		}
		return false;
	}

	/**
	 * @param userID the users id number
	 * @param pin the pin we gave the client to connect to this server
	 */
	public void addPlayer(int userID, int pin)
	{
		Player player = new Player(userID, 1234);
		players.put(userID, player);
		this.notifyObservers(new PlayerConnectionReport(player));
	}

	/**
	 * @param userID the userID of the player we are looking for
	 * @return the player we were looking for
	 */
	public Player getPlayerFromID(int userID)
	{
		return players.get(userID);
	}
}
