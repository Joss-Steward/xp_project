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

	/**
	 * I'm a singleton
	 */
	private OptionsManager()
	{
	}
	
	/**
	 * 
	 * @return The instance of OptionsManager
	 */
	public static OptionsManager getSingleton() 
	{
		if(singleton == null) 
		{
			singleton = new OptionsManager();
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
