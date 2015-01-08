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
	 * @param playerName the name of the player
	 * @param password the player's password
	 * @return the player's unique id
	 * @throws DatabaseException shouldn't
	 */
	int create(String playerName, String password) throws DatabaseException;

	/**
	 * Find the data associated with a given map file
	 * @param playerName the name of the player we are looking for
	 * @throws DatabaseException if the map file name is not found
	 */
	void find(String playerName) throws DatabaseException;

	/**
	 * 
	 * @return the player's password
	 */
	String getPassword();

	/**
	 * @param password the player's new password
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

	/**
	 * @return the player's name
	 */
	String getPlayerName();

	/**
	 * @return the player's unique ID
	 */
	int getPlayerID();

}
