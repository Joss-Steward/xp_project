package datasource;

import model.DatabaseException;

/**
 * Defines the behavior required for interfacing with a data source that has information about
 * which servers are managing which map files
 * @author Merlin
 *
 */
public interface ServerDataBehavior
{

	/**
	 * Create a new record from the parameters
	 * @param mapName the name of the map file being managed
	 * @param hostName the name of the host managing the map
	 * @param portNumber the port number associated with the map
	 * @throws DatabaseException shouldn't
	 */
	void create(String mapName, String hostName, int portNumber) throws DatabaseException;

	/**
	 * Find the data associated with a given map file
	 * @param mapFileName the name of the map file we are interested in
	 * @throws DatabaseException if the map file name is not found
	 */
	void find(String mapFileName) throws DatabaseException;

	/**
	 * 
	 * @return the name of the host that is managing the map file
	 */
	String getHostName();

	/**
	 * 
	 * @return the name of the map file we are interested in
	 */
	String getMapName();

	/**
	 * 
	 * @return the port number associated with the map file
	 */
	int getPortNumber();

}
