package edu.ship.shipsim.client.model;

/**
 * Options that are global to the client and can be looked up by anyone
 * 
 * @author Steve
 *
 */
public class OptionsManager 
{
	private static OptionsManager singleton;
	
	private String loginHost = "localhost";

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
	 * @param newHost The login server host
	 */
	public void setLoginHost(String newHost)
	{
		loginHost = newHost;
	}

	/**
	 * returns true if this server is running in localhost mode for testing purposes
	 * @return local mode
	 */
	public String getLoginHost()
	{
		return loginHost;
	}
}
