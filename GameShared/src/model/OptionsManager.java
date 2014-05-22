package model;

import java.sql.SQLException;

import communication.LocalPortMapper;

/**
 * Contain information about this server's mapping so it can be used easily in the system
 * 
 * @author Steve
 *
 */
public class OptionsManager 
{
	private static OptionsManager singleton;
	private MapToServerMapping mapping;
	
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
	 * 
	 * @param mapName The new map name
	 * @param hostName The hostname of the server
	 * @param port The port this server is on
	 * @throws SQLException When the DB operation fails
	 */
	public void updateMapInformation(String mapName, String hostName, int port) throws SQLException 
	{
		if(!testMode)
		{
			mapping = MapToServerMapping.retrieveMapping(mapName);
			mapping.setHostName(hostName);
			mapping.setMapName(mapName);
			mapping.setPortNumber(port);
			mapping.persist();
		}
		else
		{
			mapping = new MapToServerMapping();
			mapping.setHostName("localhost");
			mapping.setMapName(mapName);
			mapping.setPortNumber(new LocalPortMapper().getPortForMapName(mapName));
		}
	}

	/**
	 * 
	 * @return Our current map name
	 */
	public String getMapName() 
	{
		return mapping.getMapName();
	}
	
	/**
	 * 
	 * @return The host we have mapped to
	 */
	public String getHostName()
	{
		return mapping.getHostName();
	}
	
	/**
	 * 
	 * @return The port we have mapped to
	 */
	public int getPortNumber()
	{
		return mapping.getPortNumber();
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
