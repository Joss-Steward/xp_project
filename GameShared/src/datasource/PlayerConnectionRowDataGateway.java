package datasource;

import model.DatabaseException;

/**
 * Required functionality for all row data gateways into the PlayerConnection information
 * @author Merlin
 *
 */
public interface PlayerConnectionRowDataGateway
{

	/**
	 * Make the gateway have the information for a given playerID
	 * @param playerID the player we are interested in
	 * @throws DatabaseException
	 */
	void findPlayer(int playerID) throws DatabaseException;

	/**
	 * Store a new pin for our player
	 * @param pin the new PIN
	 * @throws DatabaseException
	 */
	void storePin(int pin) throws DatabaseException;

	/**
	 * @return this player's pin
	 */
	int getPin();

	/**
	 * set data back to a know state; used only for testing
	 */
	void resetData();

	/**
	 * @return the time when the row was last updated
	 * @throws DatabaseException if the data source has an exception
	 */
	String getChangedOn() throws DatabaseException;

	/**
	 * delete the row in the table for the current player id
	 * @throws DatabaseException if the data source has an exception
	 */
	void deleteRow() throws DatabaseException;

	/**
	 * Create a new row in the table
	 * @param playerID the player ID
	 * @param pin the player's pin
	 * @param mapName the map name that the pin is valid for
	 * @throws DatabaseException if the data source has an exception
	 */
	void create(int playerID, int pin, String mapName) throws DatabaseException;

	/**
	 * @return the name of the map associated with this connection
	 */
	String getMapName();

	/**
	 * change the name of the map associated with this connection
	 * @param string new map name
	 * @throws DatabaseException if the data source has an exception
	 */
	void storeMapName(String string) throws DatabaseException;
	
	/**
	 * Used only for testing!
	 * @param newTime the timestamp
	 * @throws DatabaseException 
	 */
	void setChangedOn(String newTime) throws DatabaseException;
	
}
