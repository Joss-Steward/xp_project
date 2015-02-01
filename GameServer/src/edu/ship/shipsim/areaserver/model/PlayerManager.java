package edu.ship.shipsim.areaserver.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import model.OptionsManager;
import model.PlayerConnection;
import model.PlayerLogin;
import model.QualifiedObservable;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import datasource.DatabaseException;
import edu.ship.shipsim.areaserver.model.reports.PlayerConnectionReport;
import edu.ship.shipsim.areaserver.model.reports.PlayerLeaveReport;

/**
 * @author Merlin
 * 
 */
public class PlayerManager extends QualifiedObservable
{
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
			for (Class<? extends QualifiedObservableReport> reportType : singleton.reportTypes)
			{
				QualifiedObservableConnector.getSingleton()
						.unregisterQualifiedObservable(singleton, reportType);
			}

			singleton.stopNpcs();
			singleton = null;
		}
	}

	private static PlayerManager singleton;

	private HashMap<Integer, Player> players;
	private List<NPC> npcs;

	private PlayerManager() throws DatabaseException
	{

		players = new HashMap<Integer, Player>();
		reportTypes.add(PlayerConnectionReport.class);

		this.registerReportTypesWeNotify();
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
			PlayerMapper mapper = new PlayerMapper(playerID);
			Player player = mapper.getPlayer();
			player.setPlayerLogin(new PlayerLogin(playerID));
			players.put(playerID, player);

			this.notifyObservers(new PlayerConnectionReport(player));
			return player;
		} catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return null;
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

		PlayerMapper pm = new PlayerMapper(playerID);
		Player player = pm.getPlayer();
		if (player.isPinValid(pin))
		{
			players.put(playerID, player);

			this.notifyObservers(new PlayerConnectionReport(player));
			return player;
		} else
		{
			throw new DatabaseException("Pin is not valid");
		}

	}

	/**
	 * @return a collection of all the players currently connected to the player
	 *         manager
	 */
	public Collection<Player> getConnectedPlayers()
	{
		return this.players.values();
	}

	/**
	 * Get a new PIN for a player so they can connect to a different area server
	 * 
	 * @param playerID
	 *            the player ID
	 * @return the pin they should use for their next connection
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public int getNewPinFor(int playerID) throws DatabaseException
	{
		PlayerConnection pin = new PlayerConnection(playerID);
		return pin.generatePin();
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
				return p.getID();
			}
		}
		throw new PlayerNotFoundException();
	}

	/**
	 * Load the npcs that belong on this map, add them to player manager, and
	 * start them
	 * 
	 * @throws DatabaseException
	 *             when database goes wrong
	 */
	public void loadNpcs() throws DatabaseException
	{
//		stopNpcs();
//		HashMap<String, Object> queryParams = new HashMap<String, Object>();
//		queryParams.put("mapName", OptionsManager.getSingleton().getMapName());
//		try
//		{
//			npcs = this.getNpcDao().queryForFieldValues(queryParams);
//			for (NPC npc : npcs)
//			{
//				npc.initializeFromDatabase();
//				players.put(npc.getID(), npc);
//				npc.start();
//			}
//		} catch (SQLException e)
//		{
//			throw new DatabaseException("Unable to load npcs");
//		}
		ArrayList<NPCMapper> pendingNPCs = NPCMapper.findNPCsOnMap(OptionsManager.getSingleton().getMapName());
		for (NPCMapper m:pendingNPCs)
		{
			NPC nextNPC = (NPC) m.getPlayer();
			players.put(nextNPC.getID(), nextNPC);
			nextNPC.start();
		}
	}

	/**
	 * @return the number of players currently on this system
	 */
	public int numberOfPlayers()
	{
		return players.size();
	}

	/**
	 * Persist the player with a given ID
	 * 
	 * @param playerID
	 *            The player id of the player to persist
	 * @return Success status of persistence
	 * @throws DatabaseException
	 *             IF we have trouble persisting to the data source
	 */
	public boolean persistPlayer(int playerID) throws DatabaseException
	{

		Player player = this.getPlayerFromID(playerID);
		player.persist();
		return true;

	}

	/**
	 * Remove a player from this server's player manager and inform all
	 * connected clients of the disconnection
	 * 
	 * @param playerID
	 *            the ID of the player we should remove
	 */
	public void removePlayer(int playerID)
	{
		Player p = this.players.remove(playerID);
		if (p != null)
		{
			// unregister this player from all observers
			QualifiedObservableConnector qoc = QualifiedObservableConnector
					.getSingleton();
			for (Class<? extends QualifiedObservableReport> type : p
					.getReportTypesWeSend())
			{
				qoc.unregisterQualifiedObservable(p, type);
			}
			System.out.println("Player " + p.getPlayerName() + " has left");
			// send the disconnect message to clients
			PlayerLeaveReport report = new PlayerLeaveReport(playerID);
			this.notifyObservers(report);
		}
	}

	/**
	 * Stop all of the npcs. This is necessary when the PlayerManager is reset
	 * so that the npcs do not have runaway timers that may be re-created.
	 */
	private void stopNpcs()
	{
		if (npcs != null)
		{
			for (NPC npc : npcs)
			{
				npc.stop();
			}
		}
	}
}
