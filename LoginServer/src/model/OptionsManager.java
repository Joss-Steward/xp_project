package model;

/**
 * Options that are global to the login server and can be looked up by anyone
 * 
 * @author Steve
 *
 */
public class OptionsManager 
{
	private static OptionsManager singleton;
	
	private boolean testMode = false;
	private boolean runningLocal = false;

	public void setRunningLocal(boolean runningLocal)
	{
		this.runningLocal = runningLocal;
	}
	/**
	 * I'm a singleton
	 * 
	 * @param testMode
	 *            true if we are running tests false for live system
	 */
	private OptionsManager(boolean testMode2)
	{
		testMode = testMode2;
	}
	/**
	 * Used to get an existing singleton (it must have already been created). If
	 * it hasn't been created, you must use the getSingleton where you specify
	 * the testing mode
	 * 
	 * @return the existing singleton
	 */
	public static OptionsManager getSingleton()
	{
		if (singleton == null)
		{
			throw new IllegalArgumentException(
					"No existing singleton - you must specify test mode");
		}
		return singleton;
	}

	public boolean isRunningLocal()
	{
		return runningLocal;
	}
	/**
	 * 
	 * @param testMode
	 *            true if we are running tests and false for live system
	 * @return The instance of OptionsManager
	 */
	public static OptionsManager getSingleton(boolean testMode)
	{
		if (singleton == null)
		{
			singleton = new OptionsManager(testMode);
		} else
		{
			if (singleton.isTestMode() != testMode)
			{
				throw new IllegalArgumentException(
						"Can't change the test mode after the singleton is created");
			}
		}
		return singleton;
	}

	
	/**
	 * Reset our instance
	 */
	public static void resetSingleton()
	{
		singleton = null;
	}
	
	/**
	 * Set this to be in localhost testing mode
	 */
	public void setTestMode() 
	{
		testMode = true;
	}

	/**
	 * returns true if this server is running in localhost mode for testing purposes
	 * @return local mode
	 */
	public boolean isTestMode()
	{
		return testMode;
	}
}
