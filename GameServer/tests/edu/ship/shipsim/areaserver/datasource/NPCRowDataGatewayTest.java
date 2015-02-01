package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;


/**
 * Tests required of all player gateways
 * @author Merlin
 *
 */
public abstract class NPCRowDataGatewayTest extends DatabaseTest
{

	private NPCRowDataGateway gateway;

	abstract NPCRowDataGateway findGateway(int playerID) throws DatabaseException;
	
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
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void finder() throws DatabaseException
	{
		NPCsForTest merlin = NPCsForTest.NPC1;
		gateway = findGateway(merlin.getPlayerID());
		assertEquals(merlin.getBehaviorClass(), gateway.getBehaviorClass());
		assertEquals(merlin.getPlayerID(), gateway.getPlayerID());
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
		gateway = findGateway(11111);
	}
	
	
}
