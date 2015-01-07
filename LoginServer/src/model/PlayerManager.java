package model;

import model.reports.LoginSuccessfulReport;
import communication.LocalPortMapper;

/**
 * @author Merlin
 * 
 */
public class PlayerManager extends QualifiedObservable
{
	final String DEFAULT_MAP = "current.tmx";

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

	private int numberOfPlayers;

	private PlayerManager()
	{

	}

	/**
	 * Attempt to login to the system. Credentials will be checked and
	 * appropriate reports will be made
	 * 
	 * @param playerName
	 *            the player's name
	 * @param password
	 *            the password
	 * @return a report giving the instructions for how the client should connect to an area server
	 * @throws LoginFailedException for database errors or invalid credentials
	 */
	public LoginSuccessfulReport login(String playerName, String password)
			throws LoginFailedException
	{
		try
		{
			PlayerLogin pl = PlayerLogin.readAndVerifyPlayerLogin(playerName,
					password);
			numberOfPlayers++;
			PlayerConnection pp = new PlayerConnection(pl.getPlayerID());

			String server;
			int port;
			if (!OptionsManager.getSingleton().isTestMode())
			{
				MapToServerMapping mapping = new MapToServerMapping(DEFAULT_MAP);
				server = mapping.getHostName();
				port = mapping.getPortNumber();
			} else
			{
				LocalPortMapper mapping = new LocalPortMapper();
				server = "localhost";
				port = mapping.getPortForMapName(DEFAULT_MAP);
			}

			LoginSuccessfulReport report = new LoginSuccessfulReport(
					pl.getPlayerID(), server, port, pp.generatePin());
			return report;
		} catch (DatabaseException e)
		{
			throw new LoginFailedException();
		}

	}

	/**
	 * @see model.QualifiedObservable#notifiesOn(java.lang.Class)
	 */
	@Override
	public boolean notifiesOn(Class<?> reportType)
	{
		
		return false;
	}

	/**
	 * @return the number of players that have logged in
	 */
	public int getNumberOfPlayers()
	{
		return numberOfPlayers;
	}
}
