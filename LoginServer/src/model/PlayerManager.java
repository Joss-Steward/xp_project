package model;

import java.sql.SQLException;

import communication.LocalPortMapper;

import model.reports.LoginFailedReport;
import model.reports.LoginSuccessfulReport;

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
		QualifiedObservableConnector.getSingleton().registerQualifiedObservable(this,
				LoginSuccessfulReport.class);
		QualifiedObservableConnector.getSingleton().registerQualifiedObservable(this,
				LoginFailedReport.class);
	}

	/**
	 * Attempt to login to the system. Credentials will be checked and
	 * appropriate reports will be made
	 * 
	 * @param playerName
	 *            the player's name
	 * @param password
	 *            the password
	 */
	public void login(String playerName, String password)
	{
		try
		{
			PlayerLogin pl = PlayerLogin.readAndVerifyPlayerLogin(playerName, password);
			numberOfPlayers++;
			PlayerPin pp = new PlayerPin(pl.getPlayerID());
			
			String server;
			int port;
			if(!OptionsManager.getSingleton().isTestMode()) 
			{
				MapToServerMapping mapping = MapToServerMapping.retrieveMapping(DEFAULT_MAP);
				server = mapping.getHostName();
				port = mapping.getPortNumber();
			}
			else
			{
				LocalPortMapper mapping = new LocalPortMapper();
				server = "localhost";
				port = mapping.getPortForMapName(DEFAULT_MAP);
			}
			
			LoginSuccessfulReport report = new LoginSuccessfulReport(pl.getPlayerID(),
					server, port, pp.generatePin());
			this.notifyObservers(report);
		} 
		catch (DatabaseException | SQLException e)
		{
			this.notifyObservers(new LoginFailedReport());
		}

	}

	/**
	 * @see model.QualifiedObservable#notifiesOn(java.lang.Class)
	 */
	@Override
	public boolean notifiesOn(Class<?> reportType)
	{
		if (reportType.equals(LoginSuccessfulReport.class)
				|| reportType.equals(LoginFailedReport.class))
		{
			return true;
		}
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
