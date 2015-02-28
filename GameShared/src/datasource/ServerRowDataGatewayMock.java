package datasource;

import java.util.HashMap;

import model.OptionsManager;

/**
 * A mock data source that returns test data.  It is initialized with the data in ServersInDB
 * @see ServersForTest
 * @author Carol
 *
 */
public class ServerRowDataGatewayMock implements ServerRowDataGateway
{

	private class Server
	{
		private String hostName;

		private int portNumber;

		/**
		 * Copy Constructor
		 * @param server the one we want to copy
		 */
		public Server(Server server)
		{
			this.hostName = server.hostName;
			this.portNumber = server.portNumber;
		}

		public Server(String hostName, int portNumber)
		{
			this.hostName = hostName;
			this.portNumber = portNumber;
		}

		public String getHostName()
		{
			return hostName;
		}

		public int getPortNumber()
		{
			return portNumber;
		}
	}
	private String mapName;
	private Server server;
	private String originalMapName;

	private static HashMap<String, Server> servers;

	/**
	 * 
	 */
	public ServerRowDataGatewayMock()
	{
		if (servers == null)
		{
			resetData();
		}
	}

	/**
	 * Find Constructor:  initializes itself with data in the data source
	 * @param mapName the map we are interested in
	 * @throws DatabaseException if we can't find data for the given map name
	 */
	public ServerRowDataGatewayMock(String mapName) throws DatabaseException
	{
		this();
		originalMapName = mapName;

		this.mapName = mapName;
		if (!servers.containsKey(mapName))
		{
			throw new DatabaseException("Couldn't find a server for map named "
					+ mapName);
		}
		this.server = new Server(servers.get(mapName));

	}

	/**
	 * Create constructor - data will be added as a new row in the data source
	 * @param mapName the name of the map
	 * @param hostName the host name serving that map
	 * @param portNumber the port number associated with that map
	 * @throws DatabaseException if an entry for this map already exists
	 */
	public ServerRowDataGatewayMock(String mapName, String hostName, int portNumber)
			throws DatabaseException
	{
		this();
		if (servers.containsKey(mapName))
		{
			throw new DatabaseException("Couldn't create a server for map named "
					+ mapName);
		}
		servers.put(mapName, new Server(hostName, portNumber));
	}

	/**
	 * @see datasource.ServerRowDataGateway#getHostName()
	 */
	@Override
	public String getHostName()
	{
		return server.getHostName();
	}

	/**
	 * @see datasource.ServerRowDataGateway#getMapName()
	 */
	@Override
	public String getMapName()
	{
		return this.mapName;
	}

	/**
	 * @see datasource.ServerRowDataGateway#getPortNumber()
	 */
	@Override
	public int getPortNumber()
	{
		return server.getPortNumber();
	}

	/**
	 * @see datasource.ServerRowDataGateway#persist()
	 */
	@Override
	public void persist()
	{
		servers.remove(this.originalMapName);
		servers.put(mapName, server);
	}

	/**
	 * @see datasource.ServerRowDataGateway#resetData()
	 */
	public void resetData()
	{
		servers = new HashMap<String, Server>();
		for (ServersForTest s : ServersForTest.values())
		{
			servers.put(s.getMapName(),
					new Server(s.getHostName(), s.getPortNumber()));
		}
	}

	/**
	 * @see datasource.ServerRowDataGateway#setHostName(java.lang.String)
	 */
	@Override
	public void setHostName(String hostName)
	{
		if (!OptionsManager.getSingleton().isRunningLocal())
		{
			server.hostName = hostName;
		}
	}

	/**
	 * @see datasource.ServerRowDataGateway#setMapName(java.lang.String)
	 */
	@Override
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	
	}

	/**
	 * @see datasource.ServerRowDataGateway#setPortNumber(int)
	 */
	@Override
	public void setPortNumber(int portNumber)
	{
		server.portNumber = portNumber;
	}
}
