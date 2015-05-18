package model;

import datasource.DatabaseException;

/**
 * Contain information about this server's mapping so it can be used easily in
 * the system
 * 
 * @author Steve
 *
 */
public class OptionsManager
{
	
	private static OptionsManager singleton;
	/**
	 * Used to get an existing singleton (it must have already been created). If
	 * it hasn't been created, you must use the getSingleton where you specify
	 * the testing mode
	 * 
	 * @return the existing singleton
	 */
	public static synchronized OptionsManager getSingleton()
	{
		if (singleton == null)
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
	private boolean testMode;
	private String mapName;
	private String hostName;

	private int portNumber;

	private String loginHost;
	private boolean usingTestDB = true;

	/**
	 * I'm a singleton
	 * 
	 * */
	private OptionsManager()
	{
		hostName = "";
	}
	/**
	 * 
	 * @return The host we have mapped to
	 */
	public String getHostName()
	{
		return hostName;
	}
	/**
	 * @return the host that is managing logins
	 */
	public String getLoginHost()
	{
		return loginHost;
	}

	/**
	 * 
	 * @return Our current map name
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * 
	 * @return The port we have mapped to
	 */
	public int getPortNumber()
	{
		return portNumber;
	}

	/**
	 * returns true if this server is running on mock data for testing
	 * purposes where appropriate
	 * 
	 * @return local mode
	 */
	public boolean isTestMode()
	{
		return testMode;
	}

	/**
	 * @return true if we are not supposed to use the production database
	 */
	public boolean isUsingTestDB()
	{
		return usingTestDB;
	}

	/**
	 * @param host the host that is managing logins
	 */
	public synchronized void setLoginHost(String host)
	{
		this.loginHost = host;
	}

	/**
	 * @param usingTestDB set to true if we are not supposed to use the production database
	 */
	public synchronized void setUsingTestDB(boolean usingTestDB)
	{
		this.usingTestDB = usingTestDB;
	}

	
	/**
	 * 
	 * @param mapName
	 *            The new map name
	 * @param hostName
	 *            The hostname of the server
	 * @param port
	 *            The port this server is on
	 * @throws DatabaseException
	 *             When the DB operation fails
	 */
	public synchronized void updateMapInformation(String mapName, String hostName, int port)
			throws DatabaseException
	{
		MapToServerMapping mapping;
		this.mapName = mapName;
		this.hostName = hostName;
		this.portNumber = port;

		mapping = new MapToServerMapping(mapName);
		if (!hostName.equals("localhost"))
		{
			mapping.setHostName(hostName);
			mapping.setMapName(mapName);
			mapping.setPortNumber(port);
			mapping.persist();
		} else
		{
			mapping.setHostName("localhost");
			mapping.setMapName(mapName);
			mapping.setPortNumber(port);
		}
			
		
	}
	/**
	 * Used when we are an area server
	 * @param hostName the hostname a server is running on
	 */
	public synchronized void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * @param b if true, we will use mock data whenever possible
	 */
	public void setTestMode(boolean b)
	{
		this.testMode = b;
	}
}
