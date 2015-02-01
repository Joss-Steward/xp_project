package edu.ship.shipsim.areaserver.datasource;

import java.util.ArrayList;
import java.util.HashMap;

import datasource.DatabaseException;

/**
 * A mock implementation for PlayerRowDataGateway
 * 
 * @author Merlin
 *
 */
public class NPCRowDataGatewayMock implements NPCRowDataGateway
{

	private class NPCInfo
	{
		private String behaviorClass;

		public NPCInfo(String behaviorClass)
		{
			this.behaviorClass = behaviorClass;
		}

		public String getBehaviorClass()
		{
			return behaviorClass;
		}

	}

	/**
	 * Map player ID to player information
	 */
	private static HashMap<Integer, NPCInfo> npcInfo;
	private int playerID;
	private NPCInfo info;

	/**
	 * Finder constructor - will initialize itself from the stored information
	 * 
	 * @param playerID
	 *            the ID of the player we are looking for
	 * @throws DatabaseException
	 *             if the playerID isn't in the data source
	 */
	public NPCRowDataGatewayMock(int playerID) throws DatabaseException
	{
		if (npcInfo == null)
		{
			resetData();
		}

		if (npcInfo.containsKey(playerID))
		{
			info = npcInfo.get(playerID);
			this.playerID = playerID;
		} else
		{
			throw new DatabaseException("Couldn't find NPC with ID " + playerID);
		}
	}

	/**
	 * Just used by tests to reset static information
	 */
	public NPCRowDataGatewayMock()
	{
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.PlayerRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		npcInfo = new HashMap<Integer, NPCInfo>();
		for (NPCsForTest p : NPCsForTest.values())
		{
			npcInfo.put(p.getPlayerID(), new NPCInfo(p.getBehaviorClass()));
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCRowDataGateway#getBehaviorClass()
	 */
	@Override
	public String getBehaviorClass()
	{
		return info.getBehaviorClass();
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.NPCRowDataGateway#getPlayerID()
	 */
	@Override
	public int getPlayerID()
	{
		return playerID;
	}

	public static ArrayList<NPCRowDataGateway> getNPCsForMap(String mapName) throws DatabaseException
	{
		if (npcInfo == null)
		{
			new NPCRowDataGatewayMock(NPCsForTest.NPC1.getPlayerID()).resetData();
		}
		ArrayList<NPCRowDataGateway> result = new ArrayList<NPCRowDataGateway>();
		for (Integer npcID:npcInfo.keySet())
		{
			PlayerRowDataGatewayMock playerGateway = new PlayerRowDataGatewayMock(npcID);
			if (playerGateway.getMapName().equals(mapName))
			{
				result.add(new NPCRowDataGatewayMock(npcID));
			}
		}
		return result;
	}

}
