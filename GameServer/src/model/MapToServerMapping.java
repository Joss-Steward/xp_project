package model;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Keeps track of which server/port number each map is being managed by
 * 
 * @author Merlin
 * 
 */
@DatabaseTable(tableName = "Server")
public final class MapToServerMapping
{

	@DatabaseField(id = true)
	private String mapName;

	@DatabaseField
	private String hostName;

	@DatabaseField
	private int portNumber;

	/**
	 * The no arg constructor required by ORMLite
	 */
	public MapToServerMapping()
	{

	}

	/**
	 * Get an object from the database
	 * 
	 * @param mapName
	 *            the name of the map for which we need the server info
	 * @return the filled out object with info from the db
	 * @throws SQLException
	 *             if there is a problem connecting to the db or the mapName is
	 *             not unique in the db
	 */
	public static MapToServerMapping retrieveMapping(String mapName) throws SQLException
	{
		getServerDao();
		return serverDao.queryForId(mapName);
	}

	private static JdbcConnectionSource connectionSource;

	private static Dao<MapToServerMapping, String> serverDao;

	/**
	 * Get the DAO that we will use to interface to the DB
	 * 
	 * @return the appropriate DAO object
	 * @throws SQLException
	 *             if we cannot connect to the server
	 */
	public static Dao<MapToServerMapping, String> getServerDao() throws SQLException
	{
		if (serverDao == null)
		{
			setUpDAOObject();
		}
		return serverDao;
	}

	private static void setUpDAOObject() throws SQLException
	{
		String databaseUrl = "jdbc:mysql://shipsim.cbzhjl6tpflt.us-east-1.rds.amazonaws.com:3306/Players";
		connectionSource = new JdbcConnectionSource(databaseUrl, "program", "ShipSim");
		serverDao = DaoManager.createDao(connectionSource, MapToServerMapping.class);
	}

	/**
	 * Get the connection source ormlite will use (only for testing)
	 * 
	 * @return the connection source
	 * @throws SQLException
	 *             if we cannot connect to the database
	 */
	public static JdbcConnectionSource getConnectionSource() throws SQLException
	{
		if (connectionSource == null)
		{
			setUpDAOObject();
		}
		return connectionSource;
	}

	/**
	 * Set the map name for this mapping
	 * 
	 * @param mapName
	 *            the map name (the name of the .tmx file)
	 */
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}

	/**
	 * The port number the server should listen to
	 * 
	 * @param portNumber
	 *            between 0 and 9999 (not enforced)
	 */
	public void setPortNumber(int portNumber)
	{
		this.portNumber = portNumber;
	}

	/**
	 * The hostname portion of the URL the server for this map will be on
	 * 
	 * @param hostName
	 *            something like mmo.cs.ship.edu
	 */
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * Get the hostname portion of the URL the server for this map will be on (something like mmo.cs.ship.edu)
	 * @return the hostname
	 */
	public String getHostName()
	{
		return hostName;
	}

	/**
	 * Get the portnumber the server managing this map will be listening to
	 * @return a port number
	 */
	public int getPortNumber()
	{
		return portNumber;
	}

	/**
	 * Get the name of the map we are interested in
	 * @return the name of the tmx file
	 */
	public String getMapName()
	{
		return mapName;
	}

	/**
	 * Persist this object out to the db
	 * @throws SQLException if updating the db failed
	 */
	public void persist() throws SQLException
	{
		serverDao.update(this);
		
	}

	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
		result = prime * result + portNumber;
		return result;
	}

	/**
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapToServerMapping other = (MapToServerMapping) obj;
		if (hostName == null)
		{
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (mapName == null)
		{
			if (other.mapName != null)
				return false;
		} else if (!mapName.equals(other.mapName))
			return false;
		if (portNumber != other.portNumber)
			return false;
		return true;
	}
}
