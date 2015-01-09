package datasource;

import model.DatabaseException;

/**
 * Defines the behavior required for interfacing with a data source that has
 * information about which servers are managing which map files
 * 
 * @author Merlin
 *
 */
public interface PlayerLoginRowDataGateway
{

	/**
	 * 
	 * @return the player's password
	 */
	String getPassword();

	/**
	 * @param password
	 *            the player's new password
	 */
	void setPassword(String password);

	/**
	 * store the information into the data source
	 * 
	 * @throws DatabaseException if the data cannot be stored into the data source
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
