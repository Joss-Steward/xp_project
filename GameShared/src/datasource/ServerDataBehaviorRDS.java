package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseException;
import model.DatabaseManager;

/**
 * A row table gateway for the Servers table in the database. That table
 * contains the information about each area server.
 * 
 * @author Merlin
 * 
 */
public class ServerDataBehaviorRDS implements ServerDataBehavior
{

	private String mapName;

	private String hostName;

	private int portNumber;

	private Connection connection;

	private String originalMapName;

	/**
	 * Create a new mapping in the DB
	 * 
	 * @param mapName
	 *            the name of the map file
	 * @param hostName
	 *            the hostname of the server that manages that map
	 * @param portNumber
	 *            the portnumber of the server that manages that map
	 * @throws DatabaseException
	 *             Either can't talk to DB or a mapping for that map file
	 *             already exists
	 */
	public void create(String mapName, String hostName, int portNumber)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("Insert INTO Server SET hostName = ?, portNumber = ?, mapName = ?");
			stmt.setString(1, hostName);
			stmt.setInt(2, portNumber);
			stmt.setString(3, mapName);
			stmt.executeUpdate();

		} catch (SQLException e)
		{
			throw new DatabaseException("Couldn't create a server for map named "
					+ mapName, e);
		}

	}

	/**
	 * Files this object with the information about the server that is managing
	 * a given map name
	 * 
	 * @param mapName
	 *            the name of the map file we are interested in
	 * @throws DatabaseException
	 *             if there is no row with the given map file name
	 */
	public void find(String mapName) throws DatabaseException
	{
		this.mapName = mapName;
		this.originalMapName = mapName;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Server WHERE mapName = ?");
			stmt.setString(1, mapName);
			ResultSet result = stmt.executeQuery();
			result.next();
			hostName = result.getString("hostName");
			portNumber = result.getInt("portNumber");

		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't find a server for map named " + mapName, e);
		}

	}

	/**
	 * Get the name of the host managing this gateway's map file
	 * 
	 * @return the host name
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * Get the name of this gateway's map file
	 * 
	 * @return the map file's name
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * Get the port number of the host managing this gateway's map file
	 * 
	 * @return the port number
	 */
	public int getPortNumber()
	{
		return portNumber;
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
		this.portNumber = portNumber;
	}

	/**
	 * @see datasource.ServerDataBehavior#setHostName(java.lang.String)
	 */
	@Override
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * @see datasource.ServerDataBehavior#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		if (!originalMapName.equals(mapName))
		{
			PreparedStatement stmt;
			try
			{
				stmt = connection
						.prepareStatement("DELETE from Server WHERE mapName = ?");
				stmt.setString(1, originalMapName);
				stmt.executeUpdate();
			} catch (SQLException e)
			{
				throw new DatabaseException("Error trying to change mapName from "
						+ originalMapName + " to " + mapName, e);
			}
			create(mapName, hostName, portNumber);
		}
		try
		{
			PreparedStatement stmt = connection
					.prepareStatement("UPDATE Server SET portNumber = ?, hostName = ? WHERE mapName = ?");
			stmt.setString(2, hostName);
			stmt.setInt(1, portNumber);
			stmt.setString(3, mapName);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't find a server for map named " + mapName, e);
		}
	}

	/**
	 * @see datasource.ServerDataBehavior#resetData()
	 */
	@Override
	public void resetData()
	{
	}

}
