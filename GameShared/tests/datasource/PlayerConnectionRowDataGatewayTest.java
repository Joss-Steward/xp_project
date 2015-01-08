package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.DatabaseException;
import model.DatabaseTest;

import org.junit.After;
import org.junit.Test;

/**
 * Tests for all of our gateways
 * @author Merlin
 *
 */
public abstract class PlayerConnectionRowDataGatewayTest extends DatabaseTest
{
	private PlayerConnectionRowDataGateway gateway;

	/**
	 * @return the gateway that we should be testing
	 */
	public abstract PlayerConnectionRowDataGateway createRowDataGateway();

	
	/**
	 * Make sure any static information is cleaned up between tests
	 */
	@After
	public void cleanup()
	{
		gateway.resetData();
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
		gateway = createRowDataGateway();
		gateway.create(42, 1414, "silly.tmx");

		PlayerConnectionRowDataGateway after = createRowDataGateway();
		after.findPlayer(42);
		assertEquals(1414, after.getPin());
//		assertEquals("1963-10-07 01:02:03", after.getChangedOn());
		assertEquals("silly.tmx", after.getMapName());
	}
	
	/**
	 * Make sure we can retrieve an existing entry
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void findExisting() throws DatabaseException
	{
		gateway = createRowDataGateway();
		gateway.findPlayer(PlayersForTest.FRANK.getPlayerID());
		assertEquals(PlayersForTest.FRANK.getPin(), gateway.getPin());
		assertEquals(PlayersForTest.FRANK.getMapNameForPin(), gateway.getMapName());
	}
	/**
	 * Make sure we can store a pin
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void storePin() throws DatabaseException
	{
		gateway = createRowDataGateway();
		gateway.findPlayer(7);
		gateway.storePin(42);
		PlayerConnectionRowDataGateway after = createRowDataGateway();
		after.findPlayer(7);
		assertEquals(42, after.getPin());
	}
	
	/**
	 * Make sure we can store a map name
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void storeMapName() throws DatabaseException
	{
		gateway = createRowDataGateway();
		gateway.findPlayer(7);
		gateway.storeMapName("dumb.tmx");
		PlayerConnectionRowDataGateway after = createRowDataGateway();
		after.findPlayer(7);
		assertEquals("dumb.tmx", after.getMapName());
	}

	/**
	 * make sure we get an exception if we try to retrieve a player that isn't there
	 * @throws DatabaseException should
	 */
	@Test(expected = DatabaseException.class)
	public void exceptionOnNoRow() throws DatabaseException
	{
		gateway = createRowDataGateway();
		gateway.findPlayer(PlayersForTest.values().length + 1);
	}

	/**
	 * Make sure we can delete the row for the current connection information
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void deleteRow() throws DatabaseException
	{
		gateway = createRowDataGateway();
		gateway.findPlayer(4);
		gateway.deleteRow();
		PlayerConnectionRowDataGateway after = createRowDataGateway();
		boolean sawException = false;
		try
		{
			after.findPlayer(4);
		} catch (DatabaseException e)
		{
			sawException = true;
		}
		assertTrue(sawException);
	}
	
	
}
