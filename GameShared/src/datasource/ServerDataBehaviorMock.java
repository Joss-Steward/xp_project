package datasource;

import java.util.HashMap;

import model.DatabaseException;

/**
 * A mock data source that returns test data.  It is initialized with the data in ServersInDB
 * @see ServersForTest
 * @author Carol
 *
 */
public class ServerDataBehaviorMock implements ServerDataBehavior
{

	private class Server
	{
		private String hostName;

		private int portNumber;

		public Server(String hostName, int portNumber)
		{
			this.hostName = hostName;
			this.portNumber = portNumber;
		}

		/**
		 * Copy Constructor
		 * @param server the one we want to copy
		 */
		public Server(Server server)
		{
			this.hostName = server.hostName;
			this.portNumber = server.portNumber;
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
	public ServerDataBehaviorMock()
	{
		if (servers == null)
		{
			resetData();
		}
	}

	/**
	 * @see datasource.ServerDataBehavior#create(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void create(String mapName, String hostName, int portNumber)
			throws DatabaseException
	{
		if (servers.containsKey(mapName))
		{
			throw new DatabaseException("Couldn't create a server for map named "
					+ mapName);
		}
		servers.put(mapName, new Server(hostName, portNumber));
	}

	/**
	 * @see datasource.ServerDataBehavior#find(java.lang.String)
	 */
	@Override
	public void find(String mapName) throws DatabaseException
	{
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
	 * @see datasource.ServerDataBehavior#getHostName()
	 */
	@Override
	public String getHostName()
	{
		return server.getHostName();
	}

	/**
	 * @see datasource.ServerDataBehavior#getMapName()
	 */
	@Override
	public String getMapName()
	{
		return this.mapName;
	}

	/**
	 * @see datasource.ServerDataBehavior#getPortNumber()
	 */
	@Override
	public int getPortNumber()
	{
		return server.getPortNumber();
	}

	/**
	 * @see datasource.ServerDataBehavior#setMapName(java.lang.String)
	 */
	@Override
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	
	}

	/**
	 * @see datasource.ServerDataBehavior#setPortNumber(int)
	 */
	@Override
	public void setPortNumber(int portNumber)
	{
		server.portNumber = portNumber;
	}

	/**
	 * @see datasource.ServerDataBehavior#setHostName(java.lang.String)
	 */
	@Override
	public void setHostName(String hostName)
	{
		server.hostName = hostName;
	}

	/**
	 * @see datasource.ServerDataBehavior#persist()
	 */
	@Override
	public void persist()
	{
		servers.remove(this.originalMapName);
		servers.put(mapName, server);
	}

	/**
	 * @see datasource.ServerDataBehavior#resetData()
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
}
