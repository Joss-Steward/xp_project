package datasource;

import static org.junit.Assert.*;
import model.DatabaseException;

import org.easymock.EasyMock;
import org.junit.Test;

/**
 * Test to make sure that ServerRowDataGateway uses its data source behavior correctly
 * @author Carol
 *
 */
public class ServerRowDataGatewayTest
{

	/**
	 * Creating a new row should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void createDefers() throws DatabaseException
	{
		ServerDataBehavior behavior = EasyMock.createNiceMock(ServerDataBehavior.class);
		behavior.create(ServersInDB.FIRST_SERVER.getMapName(),
				ServersInDB.FIRST_SERVER.getHostName(),
				ServersInDB.FIRST_SERVER.getPortNumber());
		EasyMock.replay(behavior);

		ServerRowDataGateway gateway = new ServerRowDataGateway(behavior);
		gateway.create(ServersInDB.FIRST_SERVER.getMapName(),
				ServersInDB.FIRST_SERVER.getHostName(),
				ServersInDB.FIRST_SERVER.getPortNumber());
		EasyMock.verify(behavior);
	}

	/**
	 * Finding a row should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void findDefers() throws DatabaseException
	{
		ServerDataBehavior behavior = EasyMock.createNiceMock(ServerDataBehavior.class);
		behavior.find(ServersInDB.FIRST_SERVER.getMapName());
		EasyMock.replay(behavior);

		ServerRowDataGateway gateway = new ServerRowDataGateway(behavior);
		gateway.find(ServersInDB.FIRST_SERVER.getMapName());
		EasyMock.verify(behavior);
	}

	/**
	 * All of our getters should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void gettersDefer() throws DatabaseException
	{
		ServerDataBehavior behavior = EasyMock.createNiceMock(ServerDataBehavior.class);
		behavior.find(ServersInDB.FIRST_SERVER.getMapName());
		EasyMock.expect(behavior.getMapName()).andReturn(
				ServersInDB.FIRST_SERVER.getMapName());
		EasyMock.expect(behavior.getHostName()).andReturn(
				ServersInDB.FIRST_SERVER.getHostName());
		EasyMock.expect(behavior.getPortNumber()).andReturn(
				ServersInDB.FIRST_SERVER.getPortNumber());
		EasyMock.replay(behavior);

		ServerRowDataGateway gateway = new ServerRowDataGateway(behavior);
		gateway.find(ServersInDB.FIRST_SERVER.getMapName());
		assertEquals(ServersInDB.FIRST_SERVER.getMapName(), gateway.getMapName());
		assertEquals(ServersInDB.FIRST_SERVER.getHostName(), gateway.getHostName());
		assertEquals(ServersInDB.FIRST_SERVER.getPortNumber(), gateway.getPortNumber());
	}
}
