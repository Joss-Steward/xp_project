package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import model.OptionsManager;
import model.PlayerConnection;
import model.PlayerLogin;
import model.QualifiedObservableConnector;
import model.reports.AddExistingPlayerReport;
import model.reports.PinFailedReport;
import model.reports.PlayerConnectionReport;
import model.reports.PlayerLeaveReport;
import model.reports.TimeToLevelUpDeadlineReport;
import model.reports.UpdatePlayerInformationReport;
import data.PlayerScoreRecord;
import datasource.DatabaseException;
import datasource.PlayerTableDataGatewayMock;
import datasource.PlayerTableDataGatewayRDS;

/**
 * @author Merlin
 * 
 */
public class PlayerManager
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
		if (!OptionsManager.getSingleton().isTestMode())
		{
			throw new IllegalStateException(
					"Trying to add a player without giving a PIN when not in test mode");
		}
		try
		{
			PlayerMapper mapper = new PlayerMapper(playerID);
			Player player = mapper.getPlayer();
			player.setPlayerLogin(new PlayerLogin(playerID));
			players.put(playerID, player);

			QualifiedObservableConnector.getSingleton().sendReport(
					new PlayerConnectionReport(playerID, player.getPlayerName(), player
							.getAppearanceType(), player.getPlayerPosition(), player
							.getCrew()));
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
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 */
	public Player addPlayer(int playerID, double pin) throws DatabaseException,
			IllegalQuestChangeException
	{

		PlayerMapper pm = new PlayerMapper(playerID);
		Player player = pm.getPlayer();
		if (player.isPinValid(pin))
		{
			players.put(playerID, player);

			QualifiedObservableConnector.getSingleton().sendReport(
					new PlayerConnectionReport(player.getPlayerID(), player
							.getPlayerName(), player.getAppearanceType(), player
							.getPlayerPosition(), player.getCrew()));

			QualifiedObservableConnector.getSingleton().sendReport(
					new UpdatePlayerInformationReport(player));
			tellNewPlayerAboutEveryoneElse(player);
			
			QualifiedObservableConnector.getSingleton().sendReport(
                    new TimeToLevelUpDeadlineReport(player.getPlayerID(), 
                            LevelManager.getSingleton().getLevelForPoints(player.getExperiencePoints()).getDeadlineDate(),
                            "nothing"));

			
			return player;
		} else
		{
			pm.removePlayer();
			PinFailedReport report = new PinFailedReport(playerID);
			System.err.println("Pin is not valid for " + playerID + " because "
					+ report.toString());
			QualifiedObservableConnector.getSingleton().sendReport(report);
		}
		return null;
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
				return p.getPlayerID();
			}
		}
		throw new PlayerNotFoundException();
	}

	/**
	 * @return the players with the top ten scores sorted by score
	 * @throws DatabaseException
	 *             if the data source can't get the data for us
	 */
	public ArrayList<PlayerScoreRecord> getTopTenPlayers() throws DatabaseException
	{
		if (OptionsManager.getSingleton().isTestMode())
		{
			return PlayerTableDataGatewayMock.getSingleton().getTopTenList();
		}
		return PlayerTableDataGatewayRDS.getSingleton().getTopTenList();
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
		// stopNpcs();
		// HashMap<String, Object> queryParams = new HashMap<String, Object>();
		// queryParams.put("mapName",
		// OptionsManager.getSingleton().getMapName());
		// try
		// {
		// npcs = this.getNpcDao().queryForFieldValues(queryParams);
		// for (NPC npc : npcs)
		// {
		// npc.initializeFromDatabase();
		// players.put(npc.getID(), npc);
		// npc.start();
		// }
		// } catch (SQLException e)
		// {
		// throw new DatabaseException("Unable to load npcs");
		// }
		ArrayList<NPCMapper> pendingNPCs = NPCMapper.findNPCsOnMap(OptionsManager
				.getSingleton().getMapName());
		for (NPCMapper m : pendingNPCs)
		{
			NPC nextNPC = (NPC) m.getPlayer();
			players.put(nextNPC.getPlayerID(), nextNPC);
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
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 */
	public boolean persistPlayer(int playerID) throws DatabaseException,
			IllegalQuestChangeException
	{

		Player player = this.getPlayerFromID(playerID);
		if (player != null)
		{
			player.persist();
		}
		return true;

	}

	/**
	 * Remove a player from this server's player manager and inform all
	 * connected clients of the disconnection
	 * 
	 * @param playerID
	 *            the ID of the player we should remove
	 * @throws DatabaseException
	 *             if we can't persist the player to the data source
	 * @throws IllegalQuestChangeException
	 *             the state changed illegally
	 */
	public void removePlayer(int playerID) throws DatabaseException,
			IllegalQuestChangeException
	{
		persistPlayer(playerID);
		Player p = this.players.remove(playerID);
		if (p != null)
		{

			System.out.println("Player " + p.getPlayerName() + " has left");

			// send the disconnect message to clients
			PlayerLeaveReport report = new PlayerLeaveReport(playerID);
			QualifiedObservableConnector.getSingleton().sendReport(report);
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

	private void tellNewPlayerAboutEveryoneElse(Player player)
	{
		Collection<Player> currentPlayers = players.values();
		for (Player existingPlayer : currentPlayers)
		{
			if (existingPlayer.getPlayerID() != player.getPlayerID())
			{
				AddExistingPlayerReport report = new AddExistingPlayerReport(
						player.getPlayerID(), existingPlayer.getPlayerID(),
						existingPlayer.getPlayerName(),
						existingPlayer.getAppearanceType(),
						existingPlayer.getPlayerPosition(), existingPlayer.getCrew());
				QualifiedObservableConnector.getSingleton().sendReport(report);
			}
		}
	}
}
