package model;

import java.sql.SQLException;
import java.util.HashMap;

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
	
	/*
	 * Used in testing mode to get the port from the map name
	 */
	private HashMap<String, Integer> localhostPortMapping;

	/**
	 * I'm a singleton
	 */
	private OptionsManager()
	{
		localhostPortMapping = new HashMap<String, Integer>();
		localhostPortMapping.put("current.tmx", 4000);
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
			mapping.setPortNumber(localhostPortMapping.get(mapName));
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
}
