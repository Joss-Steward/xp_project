package edu.ship.shipsim.areaserver.datasource;

import java.util.HashMap;

import datasource.DatabaseException;

/**
 * A mock version of the data sources that maps areas on a map to quests
 * @author Merlin
 *
 */
public class MapAreaRowDataGatewayMock extends MapAreaRowDataGateway
{

	private static HashMap<String, Integer> mapAreaInfo;
	private String areaName;
	private int questID;

	/**
	 * finder constructor
	 * @param areaName the name of the area we are interested in
	 * @throws DatabaseException if the areaname doesn't exist in the mock data
	 */
	public MapAreaRowDataGatewayMock(String areaName) throws DatabaseException
	{
		if (mapAreaInfo == null)
		{
			resetData();
		}

		if (mapAreaInfo.containsKey(areaName))
		{
			questID = mapAreaInfo.get(areaName);
			this.areaName = areaName;
		} else
		{
			throw new DatabaseException("Couldn't find map area with name " + areaName);
		}
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGateway#getMapAreaName()
	 */
	@Override
	public String getMapAreaName()
	{
		return areaName;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGateway#getQuestID()
	 */
	@Override
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @see edu.ship.shipsim.areaserver.datasource.MapAreaRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		mapAreaInfo = new HashMap<String, Integer>();
		for (MapAreasForTest m : MapAreasForTest.values())
		{
			mapAreaInfo.put(m.getAreaName(), m.getQuestID());
		}

	}

}
