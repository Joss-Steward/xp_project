package model;

import java.util.ArrayList;

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

	private ArrayList<Player> players;

	private PlayerManager()
	{
		players = new ArrayList<Player>();
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
		players.add(player);
		this.notifyObservers(new PlayerConnectionReport(player));
	}
}
