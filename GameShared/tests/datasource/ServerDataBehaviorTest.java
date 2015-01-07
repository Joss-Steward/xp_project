package datasource;

import static org.junit.Assert.assertEquals;

import org.junit.After;
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

	private ServerDataBehavior behavior;

	abstract ServerDataBehavior createBehavior();

	/**
	 * Make sure any static information is cleaned up between tests
	 */
	@After
	public void cleanup()
	{
		behavior.resetData();
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
		behavior = createBehavior();
		behavior.create(ServersForTest.FIRST_SERVER.getMapName(), "noHostName", 1000);
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
		behavior = createBehavior();
		behavior.create("stupid.tmx", "noHostName", 1000);
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
		ServersForTest data = ServersForTest.FIRST_SERVER;
		behavior = createBehavior();
		behavior.find(data.getMapName());
		assertEquals(data.getHostName(), behavior.getHostName());
		assertEquals(data.getPortNumber(), behavior.getPortNumber());
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
		 behavior = createBehavior();
		behavior.find("noFile");
	}

	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void editMapName() throws DatabaseException
	{
		behavior = createBehavior();
		behavior.find(ServersForTest.FIRST_SERVER.getMapName());
		behavior.setMapName("Fred.tmx");
		behavior.persist();
		ServerDataBehavior after = createBehavior();
		after.find("Fred.tmx");
		assertEquals(ServersForTest.FIRST_SERVER.getPortNumber(), after.getPortNumber());
		assertEquals(ServersForTest.FIRST_SERVER.getHostName(), after.getHostName());
	}
	/**
	 * We should be able to change the host name
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void editHostName() throws DatabaseException
	{
		behavior = createBehavior();
		behavior.find(ServersForTest.FIRST_SERVER.getMapName());
		behavior.setHostName("h");
		behavior.persist();
		ServerDataBehavior after = createBehavior();
		after.find(ServersForTest.FIRST_SERVER.getMapName());
		assertEquals(ServersForTest.FIRST_SERVER.getPortNumber(), after.getPortNumber());
		assertEquals("h", after.getHostName());
	}
	/**
	 * We should be able to change the portNumber
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void editPortNumber() throws DatabaseException
	{
		behavior = createBehavior();
		behavior.find(ServersForTest.FIRST_SERVER.getMapName());
		behavior.setPortNumber(42);
		behavior.persist();
		ServerDataBehavior after = createBehavior();
		after.find(ServersForTest.FIRST_SERVER.getMapName());
		assertEquals(42, after.getPortNumber());
		assertEquals(ServersForTest.FIRST_SERVER.getHostName(), after.getHostName());
	}
}
