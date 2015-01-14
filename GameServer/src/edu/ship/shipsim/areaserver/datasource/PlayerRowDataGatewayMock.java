package edu.ship.shipsim.areaserver.datasource;

import java.util.HashMap;

import model.DatabaseException;
import data.Position;
import datasource.PlayersForTest;

/**
 * A mock implementation for PlayerRowDataGateway
 * 
 * @author Merlin
 *
 */
public class PlayerRowDataGatewayMock implements PlayerRowDataGateway
{

	private class PlayerInfo
	{
		private String mapName;
		private Position position;
		private String appearanceType;

		public PlayerInfo(String mapName, Position position,
				String appearanceType)
		{
			this.mapName = mapName;
			this.position = position;
			this.appearanceType = appearanceType;
		}

		public String getMapName()
		{
			return mapName;
		}

		public Position getPosition()
		{
			return position;
		}

		public String getAppearanceType()
		{
			return appearanceType;
		}

	}

	/**
	 * Map player ID to player information
	 */
	private static HashMap<Integer, PlayerInfo> playerInfo;
	private static int nextKey = 1;
	private int playerID;
	private PlayerInfo info;

	/**
	 * Finder constructor - will initialize itself from the stored information
	 * 
	 * @param playerID
	 *            the ID of the player we are looking for
	 * @throws DatabaseException
	 *             if the playerID isn't in the data source
	 */
	public PlayerRowDataGatewayMock(int playerID) throws DatabaseException
	{
		if (playerInfo == null)
		{
			resetData();
		}

		if (playerInfo.containsKey(playerID))
		{
			info = playerInfo.get(playerID);
			this.playerID = playerID;
		} else
		{
			throw new DatabaseException("Couldn't find player with ID " + playerID);
		}
	}

	/**
	 * Create constructor - will add the information as a new row in the data source as
	 * the object is constructed
	 * @param mapName the name of the map the player is playing
	 * @param position the position of the player on the map
	 * @param appearanceType the appearance type of the player
	 */
	public PlayerRowDataGatewayMock(String mapName, Position position,
			String appearanceType) 
	{
		if (playerInfo == null)
		{
			resetData();
		}
		playerID = nextKey;
		nextKey++;
		
		playerInfo.put(playerID, new PlayerInfo(mapName, position,
				appearanceType));
	}

	/**
	 * Just used by tests to reset static information
	 */
	public PlayerRowDataGatewayMock()
	{
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		playerInfo = new HashMap<Integer, PlayerInfo>();
		nextKey = 1;
		for (PlayersForTest p : PlayersForTest.values())
		{
			playerInfo.put(
					nextKey,
					new PlayerInfo(p.getMapName(), p.getPosition(), p
							.getAppearanceType()));
			nextKey++;
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getMapName()
	 */
	@Override
	public String getMapName()
	{
		return info.getMapName();
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getPosition()
	 */
	@Override
	public Position getPosition()
	{
		return info.getPosition();
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#getAppearanceType()
	 */
	@Override
	public String getAppearanceType()
	{
		return info.getAppearanceType();
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#setMapName(java.lang.String)
	 */
	@Override
	public void setMapName(String mapName)
	{
		info.mapName = mapName;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#persist()
	 */
	@Override
	public void persist()
	{
		playerInfo.put(playerID, info);
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#setPosition(data.Position)
	 */
	@Override
	public void setPosition(Position position)
	{
		info.position = position;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#setAppearanceType(java.lang.String)
	 */
	@Override
	public void setAppearanceType(String appearanceType)
	{
		info.appearanceType = appearanceType;
	}

}
