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
	 * Persisting the row should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void persistDefers() throws DatabaseException
	{
		ServerDataBehavior behavior = EasyMock.createNiceMock(ServerDataBehavior.class);
		behavior.persist();
		EasyMock.replay(behavior);
		
		ServerRowDataGateway gateway = new ServerRowDataGateway(behavior);
		gateway.persist();
		EasyMock.verify(behavior);
	}
	/**
	 * Creating a new row should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void createDefers() throws DatabaseException
	{
		ServerDataBehavior behavior = EasyMock.createNiceMock(ServerDataBehavior.class);
		behavior.create(ServersForTest.FIRST_SERVER.getMapName(),
				ServersForTest.FIRST_SERVER.getHostName(),
				ServersForTest.FIRST_SERVER.getPortNumber());
		EasyMock.replay(behavior);

		ServerRowDataGateway gateway = new ServerRowDataGateway(behavior);
		gateway.create(ServersForTest.FIRST_SERVER.getMapName(),
				ServersForTest.FIRST_SERVER.getHostName(),
				ServersForTest.FIRST_SERVER.getPortNumber());
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
		behavior.find(ServersForTest.FIRST_SERVER.getMapName());
		EasyMock.replay(behavior);

		ServerRowDataGateway gateway = new ServerRowDataGateway(behavior);
		gateway.find(ServersForTest.FIRST_SERVER.getMapName());
		EasyMock.verify(behavior);
	}

	/**
	 * resetting the data behavior should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void resetDefers() throws DatabaseException
	{
		ServerDataBehavior behavior = EasyMock.createNiceMock(ServerDataBehavior.class);
		behavior.resetData();
		EasyMock.replay(behavior);

		ServerRowDataGateway gateway = new ServerRowDataGateway(behavior);
		gateway.resetData();
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
		behavior.find(ServersForTest.FIRST_SERVER.getMapName());
		EasyMock.expect(behavior.getMapName()).andReturn(
				ServersForTest.FIRST_SERVER.getMapName());
		EasyMock.expect(behavior.getHostName()).andReturn(
				ServersForTest.FIRST_SERVER.getHostName());
		EasyMock.expect(behavior.getPortNumber()).andReturn(
				ServersForTest.FIRST_SERVER.getPortNumber());
		EasyMock.replay(behavior);

		ServerRowDataGateway gateway = new ServerRowDataGateway(behavior);
		gateway.find(ServersForTest.FIRST_SERVER.getMapName());
		assertEquals(ServersForTest.FIRST_SERVER.getMapName(), gateway.getMapName());
		assertEquals(ServersForTest.FIRST_SERVER.getHostName(), gateway.getHostName());
		assertEquals(ServersForTest.FIRST_SERVER.getPortNumber(), gateway.getPortNumber());
		EasyMock.verify(behavior);
	}
	
	/**
	 * All setters should defer to the data behavior
	 * @throws DatabaseException shouldnt
	 */
	@Test
	public void settersDefer() throws DatabaseException
	{
		ServerDataBehavior behavior = EasyMock.createNiceMock(ServerDataBehavior.class);
		behavior.find(ServersForTest.FIRST_SERVER.getMapName());
		behavior.setMapName("x");
		behavior.setHostName("l");
		behavior.setPortNumber(3);
		EasyMock.replay(behavior);

		ServerRowDataGateway gateway = new ServerRowDataGateway(behavior);
		gateway.find(ServersForTest.FIRST_SERVER.getMapName());
		gateway.setMapName("x");
		gateway.setHostName("l");
		gateway.setPortNumber(3);
		EasyMock.verify(behavior);
	}

}
