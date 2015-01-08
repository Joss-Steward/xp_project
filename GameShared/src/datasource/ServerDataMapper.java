package datasource;

import model.DatabaseException;

/**
 * A row table gateway for the Servers table in the database. That table
 * contains the information about each area server. It is given a behavior that
 * defines where it gets the data from
 *
 * @author Merlin
 *
 */
public class ServerDataMapper
{

	

	private ServerRowDataGateway dataInterfaceBehavior;

	/**
	 * @param behavior
	 *            the behavior for finding the data
	 */
	public ServerDataMapper(ServerRowDataGateway behavior)
	{
		this.dataInterfaceBehavior = behavior;
	}

	/**
	 * Create a new record with information about the server that manages a
	 * given map file
	 * 
	 * @param mapName
	 *            the name of the map file
	 * @param hostName
	 *            the name of the host that manages that file
	 * @param portNumber
	 *            the name of the port number associated with that file
	 * @throws DatabaseException
	 *             shouldn't
	 */
	public void create(String mapName, String hostName, int portNumber)
			throws DatabaseException
	{
		dataInterfaceBehavior.create(mapName, hostName, portNumber);
	}

	/**
	 * Fill this object with the information the data source associates with a
	 * given map file name
	 * 
	 * @param mapFileName
	 *            the map file we are interested in
	 * @throws DatabaseException
	 *             shouldn't unless that map file isn't in the data source
	 */
	public void find(String mapFileName) throws DatabaseException
	{
		dataInterfaceBehavior.find(mapFileName);
	}

	/**
	 * @return the host name in the row
	 */
	public String getHostName()
	{
		return dataInterfaceBehavior.getHostName();
	}

	/**
	 * @return the map file in the row
	 */
	public String getMapName()
	{
		return dataInterfaceBehavior.getMapName();
	}

	/**
	 * @return the port number in the row
	 */
	public int getPortNumber()
	{
		return dataInterfaceBehavior.getPortNumber();
	}

	/**
	 * @param mapName our new map file name
	 */
	public void setMapName(String mapName)
	{
		dataInterfaceBehavior.setMapName(mapName);
		
	}

	/**
	 * @param hostName our new host name
	 */
	public void setHostName(String hostName)
	{
		dataInterfaceBehavior.setHostName(hostName);
	}

	/**
	 * @param portNumber our port number
	 */
	public void setPortNumber(int portNumber)
	{
		dataInterfaceBehavior.setPortNumber(portNumber);
	}

	/**
	 * put our information into the DB
	 * @throws DatabaseException if we have trouble talking with the DB
	 */
	public void persist() throws DatabaseException
	{
		dataInterfaceBehavior.persist();
	}

	/**
	 * resets the data to a know configuration (for testing)
	 */
	public void resetData()
	{
		dataInterfaceBehavior.resetData();
	}


	

}
