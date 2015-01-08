package model;

import datasource.PlayerLoginDataMapper;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayRDS;

/**
 * 
 * @author Merlin
 * 
 */
public class PlayerLogin
{

	private PlayerLoginDataMapper dataMapper;

	/**
	 * 
	 */
	public PlayerLogin()
	{
		if (OptionsManager.getSingleton().isTestMode())
		{
			this.dataMapper = new PlayerLoginDataMapper(
					new PlayerLoginRowDataGatewayMock());
		} else
		{
			this.dataMapper = new PlayerLoginDataMapper(
					new PlayerLoginRowDataGatewayRDS());
		}
	}

	/**
	 * Create a new record in the database
	 * 
	 * @param name
	 *            the player's name
	 * @param password
	 *            the player's password
	 * @param id
	 *            The id of the player
	 * @return the ID of the player we created
	 * @throws DatabaseException
	 *             if the gateway fails
	 */
	public static int createNewPlayerLogin(String name, String password, int id)
			throws DatabaseException
	{
		PlayerLoginDataMapper dataMapper = new PlayerLoginDataMapper(
				new PlayerLoginRowDataGatewayRDS());
		return dataMapper.create(name, password);

	}

	/**
	 * Create an object if the name and password are found in the db
	 * 
	 * @param playerName
	 *            the player's name
	 * @param password
	 *            the player's password
	 * @throws DatabaseException
	 *             if the name/password combination isn't found in the db
	 */
	public PlayerLogin(String playerName, String password) throws DatabaseException
	{
		this(playerName);
		try
		{
			dataMapper.find(playerName);
		} catch (DatabaseException e)
		{
			throw new DatabaseException("no login information for " + playerName);
		}

		if (!dataMapper.getPassword().equals(password))
		{
			throw new DatabaseException("incorrect password");
		}

	}

	/**
	 * Get a player's login information without checking his password
	 * 
	 * @param playerName
	 *            the player's player name
	 * @throws DatabaseException
	 *             if the player doesn't exist
	 */
	public PlayerLogin (String playerName) throws DatabaseException
	{
		this();
		try
		{
			dataMapper.find(playerName);
		} catch (DatabaseException e)
		{
			throw new DatabaseException("no login information for " + playerName);
		}
	}

	/**
	 * Return this player's unique ID
	 * 
	 * @return the id
	 */
	public int getPlayerID()
	{
		return dataMapper.getPlayerID();
	}

	/**
	 * Get the player's playername
	 * 
	 * @return the playername
	 */
	public String getPlayerName()
	{
		return dataMapper.getPlayerName();
	}

}
