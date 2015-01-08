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
public class ServerDataMapperTest
{

	
	/**
	 * Persisting the row should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void persistDefers() throws DatabaseException
	{
		ServerRowDataGateway gateway = EasyMock.createNiceMock(ServerRowDataGateway.class);
		gateway.persist();
		EasyMock.replay(gateway);
		
		ServerDataMapper mapper = new ServerDataMapper(gateway);
		mapper.persist();
		EasyMock.verify(gateway);
	}
	/**
	 * Creating a new row should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void createDefers() throws DatabaseException
	{
		ServerRowDataGateway gateway = EasyMock.createNiceMock(ServerRowDataGateway.class);
		gateway.create(ServersForTest.FIRST_SERVER.getMapName(),
				ServersForTest.FIRST_SERVER.getHostName(),
				ServersForTest.FIRST_SERVER.getPortNumber());
		EasyMock.replay(gateway);

		ServerDataMapper mapper = new ServerDataMapper(gateway);
		mapper.create(ServersForTest.FIRST_SERVER.getMapName(),
				ServersForTest.FIRST_SERVER.getHostName(),
				ServersForTest.FIRST_SERVER.getPortNumber());
		EasyMock.verify(gateway);
	}

	/**
	 * Finding a row should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void findDefers() throws DatabaseException
	{
		ServerRowDataGateway gateway = EasyMock.createNiceMock(ServerRowDataGateway.class);
		gateway.find(ServersForTest.FIRST_SERVER.getMapName());
		EasyMock.replay(gateway);

		ServerDataMapper mapper = new ServerDataMapper(gateway);
		mapper.find(ServersForTest.FIRST_SERVER.getMapName());
		EasyMock.verify(gateway);
	}

	/**
	 * resetting the data behavior should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void resetDefers() throws DatabaseException
	{
		ServerRowDataGateway gateway = EasyMock.createNiceMock(ServerRowDataGateway.class);
		gateway.resetData();
		EasyMock.replay(gateway);

		ServerDataMapper mapper = new ServerDataMapper(gateway);
		mapper.resetData();
		EasyMock.verify(gateway);
	}

	/**
	 * All of our getters should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void gettersDefer() throws DatabaseException
	{
		ServerRowDataGateway gateway = EasyMock.createNiceMock(ServerRowDataGateway.class);
		gateway.find(ServersForTest.FIRST_SERVER.getMapName());
		EasyMock.expect(gateway.getMapName()).andReturn(
				ServersForTest.FIRST_SERVER.getMapName());
		EasyMock.expect(gateway.getHostName()).andReturn(
				ServersForTest.FIRST_SERVER.getHostName());
		EasyMock.expect(gateway.getPortNumber()).andReturn(
				ServersForTest.FIRST_SERVER.getPortNumber());
		EasyMock.replay(gateway);

		ServerDataMapper mapper = new ServerDataMapper(gateway);
		mapper.find(ServersForTest.FIRST_SERVER.getMapName());
		assertEquals(ServersForTest.FIRST_SERVER.getMapName(), mapper.getMapName());
		assertEquals(ServersForTest.FIRST_SERVER.getHostName(), mapper.getHostName());
		assertEquals(ServersForTest.FIRST_SERVER.getPortNumber(), mapper.getPortNumber());
		EasyMock.verify(gateway);
	}
	
	/**
	 * All setters should defer to the data behavior
	 * @throws DatabaseException shouldnt
	 */
	@Test
	public void settersDefer() throws DatabaseException
	{
		ServerRowDataGateway gateway = EasyMock.createNiceMock(ServerRowDataGateway.class);
		gateway.find(ServersForTest.FIRST_SERVER.getMapName());
		gateway.setMapName("x");
		gateway.setHostName("l");
		gateway.setPortNumber(3);
		EasyMock.replay(gateway);

		ServerDataMapper mapper = new ServerDataMapper(gateway);
		mapper.find(ServersForTest.FIRST_SERVER.getMapName());
		mapper.setMapName("x");
		mapper.setHostName("l");
		mapper.setPortNumber(3);
		EasyMock.verify(gateway);
	}

}
