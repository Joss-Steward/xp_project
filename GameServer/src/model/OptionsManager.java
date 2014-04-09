package model;

import java.sql.SQLException;

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
		mapping = MapToServerMapping.retrieveMapping(mapName);
		mapping.setHostName(hostName);
		mapping.setMapName(mapName);
		mapping.setPortNumber(port);
		mapping.persist();
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

}
