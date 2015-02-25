package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;

/**
 * Tests required of all map area gateways
 * 
 * @author Merlin
 *
 */
public abstract class MapAreaRowDataGatewayTest extends DatabaseTest
{

	private MapAreaRowDataGateway gateway;

	/**
	 * Make sure any static information is cleaned up between tests
	 */
	@After
	public void cleanup()
	{
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * Make sure we can retrieve a specific question
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void finder() throws DatabaseException
	{
		MapAreasForTest area = MapAreasForTest.ONE_MAP_AREA;
		gateway = findGateway(MapAreasForTest.ONE_MAP_AREA.getAreaName());
		assertEquals(area.getAreaName(), gateway.getMapAreaName());
		assertEquals(area.getQuestID(), gateway.getQuestID());
	}

	/**
	 * make sure we get the right exception if we try to find someone who
	 * doesn't exist
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void findNotExisting() throws DatabaseException
	{
		gateway = findGateway("NOT AN AREA");
	}


	abstract MapAreaRowDataGateway findGateway(String areaName) throws DatabaseException;
}
