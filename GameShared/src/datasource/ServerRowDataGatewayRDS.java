package datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseManager;
import model.OptionsManager;

/**
 * A row table gateway for the Servers table in the database. That table
 * contains the information about each area server.
 * 
 * @author Merlin
 * 
 */
public class ServerRowDataGatewayRDS implements ServerRowDataGateway
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
	public ServerRowDataGatewayRDS(String mapName, String hostName, int portNumber)
			throws DatabaseException
	{
		insertNewRow(mapName, hostName, portNumber);
		this.mapName = mapName;
		this.hostName = hostName;
		this.portNumber = portNumber;

	}

	private void insertNewRow(String mapName, String hostName, int portNumber)
			throws DatabaseException
	{
		Connection connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"Insert INTO Server SET hostName = ?, portNumber = ?, mapName = ?");
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
	public ServerRowDataGatewayRDS(String mapName) throws DatabaseException
	{
		this.mapName = mapName;
		this.originalMapName = mapName;
		this.connection = DatabaseManager.getSingleton().getConnection();
		try
		{
			ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
					"SELECT * FROM Server WHERE mapName = ?");
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
		this.portNumber = portNumber;
	}

	/**
	 * @see datasource.ServerRowDataGateway#setHostName(java.lang.String)
	 */
	@Override
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * @see datasource.ServerRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		if (!originalMapName.equals(mapName))
		{
			ClosingPreparedStatement stmt;
			try
			{
				stmt = new ClosingPreparedStatement(connection,
						"DELETE from Server WHERE mapName = ?");
				stmt.setString(1, originalMapName);
				stmt.executeUpdate();
			} catch (SQLException e)
			{
				throw new DatabaseException("Error trying to change mapName from "
						+ originalMapName + " to " + mapName, e);
			}
			insertNewRow(mapName, hostName, portNumber);
		}
		try
		{
			if (OptionsManager.getSingleton().isRunningLocal())
			{
				ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
						"UPDATE Server SET portNumber = ? WHERE mapName = ?");
				stmt.setInt(1, portNumber);
				stmt.setString(2, mapName);
				stmt.executeUpdate();
			} else
			{
				ClosingPreparedStatement stmt = new ClosingPreparedStatement(connection,
						"UPDATE Server SET portNumber = ?, hostName = ? WHERE mapName = ?");
				stmt.setString(2, hostName);
				stmt.setInt(1, portNumber);
				stmt.setString(3, mapName);
				stmt.executeUpdate();
			}
		} catch (SQLException e)
		{
			throw new DatabaseException(
					"Couldn't find a server for map named " + mapName, e);
		}
	}

	/**
	 * @see datasource.ServerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

}
