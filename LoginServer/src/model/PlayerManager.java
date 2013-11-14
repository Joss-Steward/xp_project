package model;

import model.reports.LoginSuccessfulReport;

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

	private int numberOfPlayers;

	private PlayerManager()
	{
		QualifiedObservableConnector.getSingleton().registerQualifiedObservable(this, LoginSuccessfulReport.class);
	}

	/**
	 * Attempt to login to the system.  Credentials will be checked and appropriate reports will be made
	 * @param playerName the user name
	 * @param password the password
	 */
	public void login(String playerName, String password)
	{
		try
		{
			PlayerLogin pl = new PlayerLogin(playerName, password);
			numberOfPlayers++;
			LoginSuccessfulReport report = new LoginSuccessfulReport(42, "localhost",1872, pl.generatePin()); 
			this.notifyObservers(report);
		} catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see model.QualifiedObservable#notifiesOn(java.lang.Class)
	 */
	@Override
	public boolean notifiesOn(Class<?> reportType)
	{
		if (reportType.equals(LoginSuccessfulReport.class))
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
