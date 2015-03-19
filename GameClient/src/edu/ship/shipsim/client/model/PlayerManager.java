package edu.ship.shipsim.client.model;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Observable;

import com.badlogic.gdx.utils.IntMap;

import data.Position;
import edu.ship.shipsim.client.model.reports.LoginFailedReport;
import edu.ship.shipsim.client.model.reports.LoginInitiatedReport;
import edu.ship.shipsim.client.model.reports.PinFailedReport;
import edu.ship.shipsim.client.model.reports.PlayerConnectedToAreaServerReport;
import edu.ship.shipsim.client.model.reports.PlayerDisconnectedFromAreaServerReport;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;

/**
 * Maintains the active set of players on the area server to which this client
 * is attached
 * 
 * @author merlin
 * 
 */
public class PlayerManager extends Observable
{

	private static PlayerManager singleton;
	private IntMap<Player> playerList;

	private boolean loginInProgress;

	private PlayerManager()
	{
		thisClientsPlayer = null;
		playerList = new IntMap<Player>();
		
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
	 * 
	 * @return that player
	 */
	public ThisClientsPlayer getThisClientsPlayer()
	{
		return thisClientsPlayer;
	}

	/**
	 * Get a player with the given player id
	 * 
	 * @param playerID
	 *            the unique player id of the player in which we are interested
	 * @return the appropriate Player object or null if no such player exists
	 */
	public Player getPlayerFromID(int playerID)
	{
		return playerList.get(playerID);
	}

	/**
	 * Sets the player controlled by this client
	 * 
	 * @param playerID
	 *            ths unique identifier of the player
	 * @return the player object we created
	 * @throws AlreadyBoundException
	 *             when a login has occurred and the player is set
	 * @throws NotBoundException
	 *             when the player has not yet been set because login has not be
	 *             called
	 */
	public ThisClientsPlayer finishLogin(int playerID)
			throws AlreadyBoundException, NotBoundException
	{
		if (this.loginInProgress)
		{
			ThisClientsPlayer p = new ThisClientsPlayer(playerID);
			playerList.put(playerID, p);
			this.thisClientsPlayer = p;

			// prevent replacing the player after logging in
			loginInProgress = false;
			return p;
		} else
		{
			if (this.thisClientsPlayer == null)
			{
				throw new NotBoundException(
						"Login has not yet been called, player has not been set.");
			} else
			{
				throw new AlreadyBoundException(
						"Login has already been called, you canDave,  not reset the player until logging out.");
			}
		}
	}

	/**
	 * @return true if we are in the process of trying to log into the server
	 */
	public boolean isLoginInProgress()
	{
		return loginInProgress;
	}

	/**
	 * Attempt to login with a given name and password
	 * 
	 * @param password
	 *            the password
	 * @param name
	 *            the player's name
	 * 
	 */
	public void initiateLogin(String name, String password)
	{
		loginInProgress = true;
		QualifiedObservableConnector.getSingleton().sendReport(new LoginInitiatedReport(name, password));
	}

	/**
	 * Either create a new player with the attributes given, or update an
	 * existing player with the given playerID to have these attributes
	 * 
	 * @param playerID
	 *            The id of the player
	 * @param playerName
	 *            The name of the player
	 * @param appearanceType
	 *            The appearance type of the player
	 * @param position
	 *            The position of this player
	 * @return Player The player updated
	 */
	public Player initializePlayer(int playerID, String playerName,
			String appearanceType, Position position)
	{
		Player player = this.getPlayerFromID(playerID);
		if (player == null)
		{
			player = new Player(playerID);
			playerList.put(playerID, player);
		}
		boolean isThisClientsPlayer = false;
		if (thisClientsPlayer != null)
		{
			isThisClientsPlayer = playerID == thisClientsPlayer.getID();
		}
		PlayerConnectedToAreaServerReport report = new PlayerConnectedToAreaServerReport(
				playerID, playerName, appearanceType, position, isThisClientsPlayer);
		QualifiedObservableConnector.getSingleton().sendReport(report);

		player.setName(playerName);
		player.setAppearanceType(appearanceType);
		player.setPosition(position);

		return player;
	}
	
	/**
	 * Removes a player from being managed by this manager
	 * @param playerID
	 *            The id of the player
	 */
	public void removePlayer(int playerID)
	{
		Player player = this.playerList.remove(playerID);
		if (player != null)
		{	
			PlayerDisconnectedFromAreaServerReport report = new PlayerDisconnectedFromAreaServerReport(playerID);
			QualifiedObservableConnector.getSingleton().sendReport(report);
		}
	}
	
	/**
	 * Report when a login has failed
	 */
	public void loginFailed()
	{
		LoginFailedReport report = new LoginFailedReport("Invalid Login - Incorrect Username/Password");
		loginInProgress = false;
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * Report when a pin has failed
	 * @param err is the error message from a pin failure
	 */
	public void pinFailed(String err)
	{
		PinFailedReport report = new PinFailedReport(err);
		loginInProgress = false;
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}
}
