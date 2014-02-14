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

	private HashMap<Integer, Player> players;

	private PlayerManager()
	{
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
	 */
	public void addPlayer(int playerID, double pin)
	{
		// TODO need to check the pin . . .
		try
		{
			Player player = new Player(playerID, pin);
			players.put(playerID, player);

			this.notifyObservers(new PlayerConnectionReport(player));
		} catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
