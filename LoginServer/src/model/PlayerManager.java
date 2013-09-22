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

	private PlayerManager()
	{
		QualifiedObservableConnector.getSingleton().registerQualifiedObservable(this, LoginSuccessfulReport.class);
	}

	/**
	 * Attempt to login to the system.  Credentials will be checked and appropriate reports will be made
	 * @param userName the user name
	 * @param passWord the password
	 */
	public void login(String userName, String passWord)
	{
		//TODO when we have a db . . . verify their login and tell them where to connect to and remember the pin we gave them
		LoginSuccessfulReport report = new LoginSuccessfulReport(42, "localhost",1872, 12345); 
		this.notifyObservers(report);
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
}
