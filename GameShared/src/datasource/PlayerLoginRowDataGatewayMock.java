package datasource;

import java.util.HashMap;

import model.DatabaseException;

/**
 * This is a mock data gateway that returns data without looking at the database.  Its initial
 * data comes from PlayersForTest
 * @see PlayersForTest
 * @author Merlin
 *
 */
public class PlayerLoginRowDataGatewayMock implements PlayerLoginRowDataGateway
{

	private class PlayerInfo
	{
		private String playerName;
		private String password;

		public PlayerInfo(String playerName, String password)
		{
			this.playerName = playerName;
			this.password = password;
		}

		public String getPassword()
		{
			return password;
		}

		public String getPlayerName()
		{
			return playerName;
		}
	}

	/**
	 * Map player ID to player information
	 */
	private static HashMap<Integer, PlayerInfo> playerLogins;
	private static int nextKey = 1;
	private int playerID;
	private PlayerInfo playerInfo;

	
	/**
	 * 
	 */
	public PlayerLoginRowDataGatewayMock()
	{
		if (playerLogins == null)
		{
			resetData();
		}
	}
	/**
	 * @see datasource.PlayerLoginRowDataGateway#create(java.lang.String, java.lang.String)
	 */
	@Override
	public int create(String playerName, String password) throws DatabaseException
	{
		playerID = nextKey;
		nextKey++;
		for (PlayerInfo info : playerLogins.values())
		{
			if (info.getPlayerName().equals(playerName))
			{
				throw new DatabaseException("Tried to create duplicate user "
						+ playerName);
			}
		}
		playerLogins.put(nextKey, new PlayerInfo(playerName, password));
		return playerID;
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#find(java.lang.String)
	 */
	@Override
	public void find(String playerName) throws DatabaseException
	{
		for (Integer key:playerLogins.keySet())
		{
			PlayerInfo info =playerLogins.get(key);
			if (info.getPlayerName().equals(playerName))
			{
				playerID = key; 
				playerInfo = info;
				return;
			}
		}
		throw new DatabaseException("Couldn't find player named " + playerName);
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPassword()
	 */
	@Override
	public String getPassword()
	{
		return playerInfo.getPassword();
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPlayerName()
	 */
	@Override
	public String getPlayerName()
	{
		return playerInfo.getPlayerName();
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see datasource.PlayerLoginRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		playerLogins.put(playerID, playerInfo);
	}
	/**
	 * @see datasource.PlayerLoginRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		playerLogins = new HashMap<Integer, PlayerInfo>();
		nextKey = 1; 
		for (PlayersForTest p : PlayersForTest.values())
		{
			playerLogins.put(nextKey, new PlayerInfo(p.getPlayerName(), p.getPlayerPassword()));
			nextKey++;
		}
	}
	/**
	 * @see datasource.PlayerLoginRowDataGateway#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password)
	{
		this.playerInfo.password = password;
	}

}
