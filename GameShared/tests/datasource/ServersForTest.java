package datasource;

/**
 * Used to initialize the database to have dummy data for map to server mappings
 * 
 * @author Merlin
 *
 */
public enum ServersForTest
{
	/**
	 * 
	 */
	FIRST_SERVER("map1.tmx", "host1.com", 1871),
	/**
	 * 
	 */
	SECOND_SERVER("map2.tmx", "host2.com", 1872),
	/**
	 *
	 */
	CURRENT("current.tmx", "localhost", 1872),
	/**
	 * 
	 */
	QUIZNASIUM("quiznasium.tmx", "localhost", 1873),
	/**
	 * 
	 */
	HOMEWORK("homework.tmx", "localhost", 1874);
	private String mapName;

	private String hostName;

	private int portNumber;

	ServersForTest(String mapName, String hostName, int portNumber)
	{
		this.mapName = mapName;
		this.hostName = hostName;
		this.portNumber = portNumber;
	}

	/**
	 * Get the host name of the server that should manage that map
	 * 
	 * @return the host name portion of the server's url
	 */
	public String getHostName()
	{
		return hostName;
	}
	/**
	 * get the map this mapping relates to
	 * 
	 * @return the file name of the tmx file
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * The port number that the server managing this map will listen to
	 * 
	 * @return the port number
	 */
	public int getPortNumber()
	{
		return portNumber;
	}

}
