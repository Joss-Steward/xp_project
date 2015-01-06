package datasource;

import static org.junit.Assert.assertEquals;
import model.DatabaseException;
import model.DatabaseTest;

import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class ServerRowDataGatewayTest extends DatabaseTest
{

	/**
	 * Get a gateway for a row that we know is in the database
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void readExisting() throws DatabaseException
	{
		ServersInDB data = ServersInDB.FIRST_SERVER;
		ServerRowDataGateway gateway = ServerRowDataGateway.find(data.getMapName());
		assertEquals(data.getHostName(), gateway.getHostName());
		assertEquals(data.getPortNumber(), gateway.getPortNumber());
	}

	/**
	 * If we ask for a gateway for a row that isn't in the database, we should get an exception
	 * @throws DatabaseException should
	 */
	@Test(expected = DatabaseException.class)
	public void findNotExisting() throws DatabaseException
	{
		ServerRowDataGateway.find("noFile");
	}
	
	/**
	 * Create a new row in the database 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void createNotExisting() throws DatabaseException
	{
		ServerRowDataGateway.create("stupid.tmx","noHostName",1000);
		ServerRowDataGateway found = ServerRowDataGateway.find("stupid.tmx");
		assertEquals("noHostName",found.getHostName());
		assertEquals(1000, found.getPortNumber());
	}
	
	/**
	 * Try to create a row for a map file that is already in the database
	 * @throws DatabaseException shouldn't
	 */
	@Test(expected = DatabaseException.class)
	public void createExisting() throws DatabaseException
	{
		ServerRowDataGateway.create( ServersInDB.FIRST_SERVER.getMapName(),"noHostName",1000);
	}
}
