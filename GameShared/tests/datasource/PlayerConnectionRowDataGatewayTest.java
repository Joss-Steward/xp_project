package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

/**
 * Tests for all of our gateways
 * 
 * @author Merlin
 *
 */
public abstract class PlayerConnectionRowDataGatewayTest extends DatabaseTest
{
	private PlayerConnectionRowDataGateway gateway;

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
	 * Make sure we can add a new user to the system
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void creation() throws DatabaseException
	{
		gateway = createRowDataGateway(42, 1414, "silly.tmx");

		PlayerConnectionRowDataGateway after = findRowDataGateway(42);
		assertEquals(1414, after.getPin());
		// assertEquals("1963-10-07 01:02:03", after.getChangedOn());
		assertEquals("silly.tmx", after.getMapName());
	}

	/**
	 * Make sure we can delete the row for the current connection information
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void deleteRow() throws DatabaseException
	{
		gateway = findRowDataGateway(4);
		gateway.deleteRow();
		boolean sawException = false;
		try
		{
			findRowDataGateway(4);
		} catch (DatabaseException e)
		{
			sawException = true;
		}
		assertTrue(sawException);
	}

	/**
	 * make sure we get an exception if we try to retrieve a player that isn't
	 * there
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void exceptionOnNoRow() throws DatabaseException
	{
		gateway = findRowDataGateway(PlayersForTest.values().length + 1);
	}

	/**
	 * Make sure we can retrieve an existing entry
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void findExisting() throws DatabaseException
	{
		gateway = findRowDataGateway(PlayersForTest.FRANK.getPlayerID());
		assertEquals(PlayersForTest.FRANK.getPin(), gateway.getPin());
		assertEquals(PlayersForTest.FRANK.getMapName(), gateway.getMapName());
	}

	/**
	 * Make sure we can store a map name
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void storeMapName() throws DatabaseException
	{
		gateway = findRowDataGateway(7);
		gateway.storeMapName("dumb.tmx");
		PlayerConnectionRowDataGateway after = findRowDataGateway(7);
		assertEquals("dumb.tmx", after.getMapName());
	}

	/**
	 * Make sure we can store a pin
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void storePin() throws DatabaseException
	{
		gateway = findRowDataGateway(7);
		gateway.storePin(42);
		PlayerConnectionRowDataGateway after = findRowDataGateway(7);
		assertEquals(42, after.getPin());
	}

	/**
	 * Creates a data source that stores the given information permanently
	 * @param playerID the player's unique ID
	 * @param pin the pin the player needs to connect
	 * @param mapFileName the map of the area the player is connecting to
	 * @return the new gateway for that row
	 * @throws DatabaseException if the data source cannot create the row
	 */
	abstract PlayerConnectionRowDataGateway createRowDataGateway(int playerID, int pin, String mapFileName) throws DatabaseException;

	/**
	 * Create a row data gateway for an existing row in the table
	 * @param playerID the player's unique ID
	 * @return the gateway that we should be testing
	 * @throws DatabaseException if the data source cannot find the player
	 */
	abstract PlayerConnectionRowDataGateway findRowDataGateway(int playerID)
			throws DatabaseException;

}
