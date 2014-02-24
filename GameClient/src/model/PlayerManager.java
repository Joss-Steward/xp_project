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
	private HashMap<Integer, Player> playerList;

	private PlayerManager()
	{
		thisClientsPlayer = new ThisClientsPlayer();
		playerList = new HashMap<Integer,Player>();
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
	 * Get a player with the given player id
	 * @param playerID the unique player id of the player in which we are interested
	 * @return the appropriate Player object or null if no such player exists
	 */
	public Player getPlayerFromID(int playerID)
	{
		return playerList.get(playerID);
	}

	/**
	 * Add a new player to the list of active players
	 * @param playerID the unique name of the player we should add
	 */
	public void addPlayer(int playerID)
	{
		Player p = new Player(playerID);
		playerList.put(playerID,p);
	}

}
