package datasource;

import static org.junit.Assert.*;
import model.DatabaseException;
import model.DatabaseTest;

import org.junit.After;
import org.junit.Test;

/**
 * @author Carol Tests for all of the Player Login row data gateways
 */
public abstract class PlayerLoginRowDataGatewayTest extends DatabaseTest
{
	abstract PlayerLoginRowDataGateway createRowDataGateway();

	PlayerLoginRowDataGateway gateway;

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
		gateway.create("sillyUser", "secret");

		PlayerLoginRowDataGateway after = createRowDataGateway();
		after.find("sillyUser");
		assertEquals("secret", after.getPassword());
	}

	/**
	 * Try to create a row for a map file that is already in the database
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test(expected = DatabaseException.class)
	public void createExisting() throws DatabaseException
	{
		gateway = createRowDataGateway();
		gateway.create(PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getPlayerPassword());

	}

	/**
	 * Make sure we can retrieve an existing player
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void findExisting() throws DatabaseException
	{
		gateway = createRowDataGateway();
		gateway.find(PlayersForTest.MERLIN.getPlayerName());
		assertEquals(2, gateway.getPlayerID());
		assertEquals(PlayersForTest.MERLIN.getPlayerName(), gateway.getPlayerName());
		assertEquals(PlayersForTest.MERLIN.getPlayerPassword(), gateway.getPassword());
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
		gateway = createRowDataGateway();
		gateway.find("no one");
	}

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void changePassword() throws DatabaseException
	{
		gateway = createRowDataGateway();
		gateway.find(PlayersForTest.MERLIN.getPlayerName());
		gateway.setPassword("not secret");
		gateway.persist();
		PlayerLoginRowDataGateway after = createRowDataGateway();
		after.find(PlayersForTest.MERLIN.getPlayerName());
		assertEquals("not secret", after.getPassword());
	}
}