package model;

import datasource.DatabaseException;
import datasource.PlayerLoginRowDataGateway;
import datasource.PlayerLoginRowDataGatewayMock;
import datasource.PlayerLoginRowDataGatewayRDS;

/**
 * 
 * @author Merlin
 * 
 */
public class PlayerLogin
{

	private PlayerLoginRowDataGateway gateway;

	/**
	 * Create a new record in the database
	 * 
	 * @param name
	 *            the player's name
	 * @param password
	 *            the player's password
	 * @param id
	 *            The id of the player 
	 * @throws DatabaseException
	 *             if the gateway fails
	 */
	public PlayerLogin(String name, String password, int id) throws DatabaseException
	{
		try
		{
			if (OptionsManager.getSingleton().isTestMode())
			{
				this.gateway = new PlayerLoginRowDataGatewayMock(name, password);
			} else
			{
				this.gateway = new PlayerLoginRowDataGatewayRDS(name, password);
			}
		} catch (DatabaseException e)
		{
			throw new DatabaseException("no login information for " + name);
		}

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
		try
		{
			if (OptionsManager.getSingleton().isTestMode())
			{
				this.gateway = new PlayerLoginRowDataGatewayMock(playerName);
			} else
			{
				this.gateway = new PlayerLoginRowDataGatewayRDS(playerName);
			}
		} catch (DatabaseException e)
		{
			throw new DatabaseException("no login information for " + playerName);
		}

		if (!gateway.getPassword().equals(password))
		{
			throw new DatabaseException("incorrect password");
		}

	}

	/**
	 * Get a player's login information without checking his password
	 * 
	 * @param playerID
	 *            the player's unique ID
	 * @throws DatabaseException
	 *             if the player doesn't exist
	 */
	public PlayerLogin(int playerID) throws DatabaseException
	{
		try
		{

			if (OptionsManager.getSingleton().isTestMode())
			{
				this.gateway = new PlayerLoginRowDataGatewayMock(playerID);
			} else
			{
				this.gateway = new PlayerLoginRowDataGatewayRDS(playerID);
			}
		} catch (DatabaseException e)
		{
			throw new DatabaseException("no login information for player with ID " + playerID);
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
	public PlayerLogin(String playerName) throws DatabaseException
	{
		try
		{

			if (OptionsManager.getSingleton().isTestMode())
			{
				this.gateway = new PlayerLoginRowDataGatewayMock(playerName);
			} else
			{
				this.gateway = new PlayerLoginRowDataGatewayRDS(playerName);
			}
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
		return gateway.getPlayerID();
	}

	/**
	 * Get the player's playername
	 * 
	 * @return the playername
	 */
	public String getPlayerName()
	{
		return gateway.getPlayerName();
	}

}
