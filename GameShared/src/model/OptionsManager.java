package model;

import communication.LocalPortMapper;

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
	private boolean testMode;
	private String mapName;
	private String hostName;
	private int portNumber;

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
	public void updateMapInformation(String mapName, String hostName, int port)
			throws DatabaseException
	{
		MapToServerMapping mapping;
		this.mapName = mapName;
		this.hostName = hostName;
		this.portNumber = port;
		if (!testMode)
		{
			mapping = new MapToServerMapping(mapName);
			mapping.setHostName(hostName);
			mapping.setMapName(mapName);
			mapping.setPortNumber(port);
			mapping.persist();
		} else
		{
			mapping = new MapToServerMapping(mapName);
			mapping.setHostName("localhost");
			this.hostName = "localhost";
			mapping.setMapName(mapName);
			mapping.setPortNumber(new LocalPortMapper().getPortForMapName(mapName));
			mapping.persist();
		}
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
	 * @return The host we have mapped to
	 */
	public String getHostName()
	{
		return hostName;
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
	 * returns true if this server is running in localhost mode for testing
	 * purposes
	 * 
	 * @return local mode
	 */
	public boolean isTestMode()
	{
		return testMode;
	}
}
