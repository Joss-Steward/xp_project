package datasource;

import model.DatabaseException;

/**
 * @author Merlin
 *
 */
public class PlayerLoginDataMapper
{

	private PlayerLoginRowDataGateway gateway;

	/**
	 * @param gateway the gateway to our data
	 */
	public PlayerLoginDataMapper(PlayerLoginRowDataGateway gateway)
	{
		this.gateway = gateway;
	}

	/**
	 * Tell the gateway to persist to the data source
	 * @throws DatabaseException if the gateway has a failure
	 */
	public void persist() throws DatabaseException
	{
		gateway.persist();
	}

	/**
	 * Create a new player login entry in the data source
	 * @param playerName the name of the player
	 * @param password the player's password
	 * @return the player's unique ID
	 * @throws DatabaseException if the gateway has a failure
	 */
	public int create(String playerName, String password) throws DatabaseException
	{
		return gateway.create(playerName, password);
	}

	/**
	 * Make this object find a retain the information about a particular player
	 * @param playerName the name of the player
	 * @throws DatabaseException if the gateway has an error
	 */
	public void find(String playerName) throws DatabaseException
	{
		gateway.find(playerName);
	}

	/**
	 * Tell the gateway to go to a known state (for testing)
	 */
	public void resetData()
	{
		gateway.resetData();
	}

	/**
	 * @return this player's current password
	 */
	public String getPassword()
	{
		return gateway.getPassword();
	}

	/**
	 * @return this player's unique ID
	 */
	public int getPlayerID()
	{
		return gateway.getPlayerID();
	}

	/**
	 * get the name of this player
	 * @return this player's player name
	 */
	public String getPlayerName()
	{
		return gateway.getPlayerName();
	}

	/**
	 * Change the password for this user
	 * @param password the new password
	 */
	public void setPassword(String password)
	{
		gateway.setPassword(password);
	}

}
