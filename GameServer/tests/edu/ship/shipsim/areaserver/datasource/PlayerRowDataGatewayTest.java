package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.*;
import model.DatabaseException;
import model.DatabaseTest;

import org.junit.After;
import org.junit.Test;

import data.Position;
import datasource.PlayersForTest;

/**
 * Tests required of all player gateways
 * @author Merlin
 *
 */
public abstract class PlayerRowDataGatewayTest extends DatabaseTest
{

	private PlayerRowDataGateway gateway;

	abstract PlayerRowDataGateway findGateway(int playerID) throws DatabaseException;
	
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
		PlayersForTest merlin = PlayersForTest.MERLIN;
		gateway = findGateway(merlin.getPlayerID());
		assertEquals(merlin.getMapName(), gateway.getMapName());
		assertEquals(merlin.getPlayerID(), gateway.getPlayerID());
		assertEquals(merlin.getPosition(), gateway.getPosition());
		assertEquals(merlin.getAppearanceType(), gateway.getAppearanceType());
	}

	/**
	 * Make sure we can add a new user to the system
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		gateway = createGateway("mapName", new Position(3,2), "Warrior");

		PlayerRowDataGateway after = findGateway(gateway.getPlayerID());
		assertEquals("mapName", after.getMapName());
		assertEquals(new Position(3,2), after.getPosition());
		assertEquals("Warrior", after.getAppearanceType());
	}

	abstract PlayerRowDataGateway createGateway(String mapName, Position position,
			String appearanceType) throws DatabaseException;
	
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
	
	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void changeMapName() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setMapName("no where");
		gateway.persist();
		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN
				.getPlayerID());
		assertEquals("no where", after.getMapName());
	}
	
	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void changePosition() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setPosition(new Position(42,32));
		gateway.persist();
		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN
				.getPlayerID());
		assertEquals(new Position(42,32), after.getPosition());
	}
	
	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void changeAppearanceType() throws DatabaseException
	{
		gateway = findGateway(PlayersForTest.MERLIN.getPlayerID());
		gateway.setAppearanceType("Ugly!");
		gateway.persist();
		PlayerRowDataGateway after = findGateway(PlayersForTest.MERLIN
				.getPlayerID());
		assertEquals("Ugly!", after.getAppearanceType());
	}
}
