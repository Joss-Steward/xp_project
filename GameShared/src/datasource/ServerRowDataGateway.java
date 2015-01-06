package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DatabaseException;
import model.DatabaseManager;

/**
 * A row table gateway for the Servers table in the database.  That table contains the information about each area server.
 * @author Merlin
 * 
 */
public class ServerRowDataGateway
{

	/**
	 * Create a new mapping in the DB
	 * @param mapName the name of the map file
	 * @param hostName the hostname of the server that manages that map
	 * @param portNumber the portnumber of the server that manages that map
	 * @throws DatabaseException Either can't talk to DB or a mapping for that map file already exists
	 */
	public static void create(String mapName, String hostName, int portNumber)
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
	 * Find the server that is managing a given map name
	 * @param mapName the name of the map file we are interested in
	 * @return a ServerRowDataGateway for the row of the server table associated with that map file
	 * @throws DatabaseException if there is no row with the given map file name
	 */
	public static ServerRowDataGateway find(String mapName) throws DatabaseException
	{

		return new ServerRowDataGateway(mapName);
	}

	private String mapName;

	private String hostName;

	private int portNumber;
	
	private Connection connection;

	private ServerRowDataGateway(String mapName) throws DatabaseException
	{
		this.mapName = mapName;
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
	 * @return the host name
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * Get the name of this gateway's map file
	 * @return the map file's name
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * Get the port number of the host managing this gateway's map file
	 * @return the port number
	 */
	public int getPortNumber()
	{
		return portNumber;
	}

}
