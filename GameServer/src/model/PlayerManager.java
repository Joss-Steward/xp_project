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
	public void addPlayer(int userID, double pin)
	{
		Player player = new Player(userID, pin);
		players.put(userID, player);
		this.notifyObservers(new PlayerConnectionReport(player));
	}

	/**
	 * @param userID the userID of the player we are looking for
	 * @return the player we were looking for
	 */
	public Player getPlayerFromID(int userID)
	{
		// TODO should throw player not found exception if it isn't here
		return players.get(userID);
	}

	/**
	 * @param userName the user name of the player we are searching for
	 * @return the userID of the player we are searching for
	 * @throws PlayerNotFoundException if no player with that username is found
	 */
	public int getUserIDFromUserName(String userName) throws PlayerNotFoundException
	{
		java.util.Iterator<Player> i = players.values().iterator();
		while(i.hasNext())
		{
			Player p = i.next();
			if (p.getUserName().equals(userName))
			{
				return p.getUserID();
			}
		}
		throw new PlayerNotFoundException();
	}
}
