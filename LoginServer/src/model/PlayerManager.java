package model;

import java.sql.SQLException;

import model.reports.LoginFailedReport;
import model.reports.LoginSuccessfulReport;

/**
 * @author Merlin
 * 
 */
public class PlayerManager extends QualifiedObservable
{
	private String STARTING_MAP = "current.tmx";
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
			
			MapToServerMapping mapping = MapToServerMapping.retrieveMapping(STARTING_MAP);
			
			LoginSuccessfulReport report = new LoginSuccessfulReport(pl.getPlayerID(),
					mapping.getHostName(), mapping.getPortNumber(), pp.generatePin());
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
