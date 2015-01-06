package datasource;

import java.util.HashMap;

import model.DatabaseException;

/**
 * A mock data source that returns test data.  It is initialized with the data in ServersInDB
 * @see ServersInDB
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

		public String getHostName()
		{
			return hostName;
		}

		public int getPortNumber()
		{
			return portNumber;
		}
	}
	private String mapFileName;
	private Server server;

	private static HashMap<String, Server> servers;

	ServerDataBehaviorMock()
	{
		if (servers == null)
		{
			servers = new HashMap<String, Server>();
			for (ServersInDB s : ServersInDB.values())
			{
				servers.put(s.getMapName(),
						new Server(s.getHostName(), s.getPortNumber()));
			}
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
	public void find(String mapFileName) throws DatabaseException
	{
		this.mapFileName = mapFileName;
		if (!servers.containsKey(mapFileName))
		{
			throw new DatabaseException("Couldn't find a server for map named "
					+ mapFileName);
		}
		this.server = servers.get(mapFileName);

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
		return this.mapFileName;
	}

	/**
	 * @see datasource.ServerDataBehavior#getPortNumber()
	 */
	@Override
	public int getPortNumber()
	{
		return server.getPortNumber();
	}
}
