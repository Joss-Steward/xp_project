package datasource;

import model.DatabaseException;

/**
 * Defines the behavior required for interfacing with a data source that has information about
 * which servers are managing which map files
 * @author Merlin
 *
 */
public interface PlayerLoginRowDataGateway
{

	/**
	 * Create a new record from the parameters
	 * @throws DatabaseException shouldn't
	 */
	int create(String playerName, String password) throws DatabaseException;

	/**
	 * Find the data associated with a given map file
	 * @throws DatabaseException if the map file name is not found
	 */
	void find(String playerName) throws DatabaseException;

	/**
	 * 
	 * @return
	 */
	String getPassword();

	/**
	 * @param mapName the new map name for this server
	 */
	void setPassword(String password);

	/**
	 * store the information into the data source
	 * @throws DatabaseException
	 */
	void persist() throws DatabaseException;

	/**
	 * Initialize the data source to a known state (for testing)
	 */
	void resetData();

	String getPlayerName();

	int getPlayerID();

}
