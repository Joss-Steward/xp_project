package datasource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.DatabaseException;
import model.DatabaseTest;

/**
 * Tests the behaviors associated with Server data sources
 * @author Merlin
 *
 */
public abstract class ServerDataBehaviorTest extends DatabaseTest
{

	abstract ServerDataBehavior createBehavior();

	/**
	 * Try to create a row for a map file that is already in the database
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test(expected = DatabaseException.class)
	public void createExisting() throws DatabaseException
	{
		ServerDataBehavior gateway = createBehavior();
		gateway.create(ServersInDB.FIRST_SERVER.getMapName(), "noHostName", 1000);
	}

	/**
	 * Create a new row in the database
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void createNotExisting() throws DatabaseException
	{
		// Use one behavior to create it
		ServerDataBehavior gateway = createBehavior();
		gateway.create("stupid.tmx", "noHostName", 1000);
		// Make sure another behavior can find it
		ServerDataBehavior found = createBehavior();
		found.find("stupid.tmx");
		assertEquals("noHostName", found.getHostName());
		assertEquals(1000, found.getPortNumber());
	}

	/**
	 * Get a gateway for a row that we know is in the database
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void findExisting() throws DatabaseException
	{
		ServersInDB data = ServersInDB.FIRST_SERVER;
		ServerDataBehavior gateway = createBehavior();
		gateway.find(data.getMapName());
		assertEquals(data.getHostName(), gateway.getHostName());
		assertEquals(data.getPortNumber(), gateway.getPortNumber());
	}

	/**
	 * If we ask for a gateway for a row that isn't in the database, we should
	 * get an exception
	 * 
	 * @throws DatabaseException
	 *             should
	 */
	@Test(expected = DatabaseException.class)
	public void findNotExisting() throws DatabaseException
	{
		ServerDataBehavior gateway = createBehavior();
		gateway.find("noFile");
	}

}
