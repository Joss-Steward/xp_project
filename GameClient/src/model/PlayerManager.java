package model;

import java.util.HashMap;

/**
 * Maintains the active set of players on the area server to which this client
 * is attached
 * 
 * @author merlin
 * 
 */
public class PlayerManager
{

	private static PlayerManager singleton;
	private HashMap<String, Player> playerList;

	private PlayerManager()
	{
		thisClientsPlayer = new ThisClientsPlayer();
		playerList = new HashMap<String,Player>();
	}

	/**
	 * There should be only one
	 * 
	 * @return the only player
	 */
	public static synchronized PlayerManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new PlayerManager();
		}
		return singleton;
	}

	private ThisClientsPlayer thisClientsPlayer;
	/**
	 * Used only in testing to re-initialize the singleton
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}

	/**
	 * Get the player that is playing on this client
	 * @return that player
	 */
	public ThisClientsPlayer getThisClientsPlayer()
	{
		return thisClientsPlayer;
	}

	/**
	 * Get a player with the given user name
	 * @param userName the unique user name of the player in which we are interested
	 * @return the appropriate Player object or null if no such player exists
	 */
	public Player getPlayerNamed(String userName)
	{
		return playerList.get(userName);
	}

	/**
	 * Add a new player to the list of active players
	 * @param playerName the unique name of the player we should add
	 */
	public void addPlayer(String playerName)
	{
		Player p = new Player(playerName);
		playerList.put(p.getPlayerName(),p);
	}

}
