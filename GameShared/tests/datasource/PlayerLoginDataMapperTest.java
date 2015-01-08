package datasource;

import static org.junit.Assert.*;
import model.DatabaseException;

import org.easymock.EasyMock;
import org.junit.Test;

/**
 * @author Merlin
 *
 */
public class PlayerLoginDataMapperTest
{

	/**
	 * Persisting the row should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void persistDefers() throws DatabaseException
	{
		PlayerLoginRowDataGateway gateway = EasyMock.createNiceMock(PlayerLoginRowDataGateway.class);
		gateway.persist();
		EasyMock.replay(gateway);
		
		PlayerLoginDataMapper mapper = new PlayerLoginDataMapper(gateway);
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
		PlayerLoginRowDataGateway behavior = EasyMock.createNiceMock(PlayerLoginRowDataGateway.class);
		EasyMock.expect(behavior.create("me",
				"secret")).andReturn(42);
		EasyMock.replay(behavior);

		PlayerLoginDataMapper mapper = new PlayerLoginDataMapper(behavior);
		assertEquals(42,mapper.create("me", "secret"));
		EasyMock.verify(behavior);
	}
	
	/**
	 * Finding a row should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void findDefers() throws DatabaseException
	{
		PlayerLoginRowDataGateway gateway = EasyMock.createNiceMock(PlayerLoginRowDataGateway.class);
		gateway.find(PlayersForTest.MERLIN.getPlayerName());
		EasyMock.replay(gateway);

		PlayerLoginDataMapper mapper = new PlayerLoginDataMapper(gateway);
		mapper.find(PlayersForTest.MERLIN.getPlayerName());
		EasyMock.verify(gateway);
	}
	
	/**
	 * resetting the data behavior should defer to the data behavior
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void resetDefers() throws DatabaseException
	{
		PlayerLoginRowDataGateway gateway = EasyMock.createNiceMock(PlayerLoginRowDataGateway.class);
		gateway.resetData();
		EasyMock.replay(gateway);

		PlayerLoginDataMapper mapper = new PlayerLoginDataMapper(gateway);
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
		PlayerLoginRowDataGateway behavior = EasyMock.createNiceMock(PlayerLoginRowDataGateway.class);
		behavior.find(PlayersForTest.MERLIN.getPlayerName());
		EasyMock.expect(behavior.getPassword()).andReturn(
				PlayersForTest.MERLIN.getPlayerPassword());
		EasyMock.expect(behavior.getPlayerID()).andReturn(
				PlayersForTest.MERLIN.getPlayerID());
		EasyMock.expect(behavior.getPlayerName()).andReturn(
				PlayersForTest.MERLIN.getPlayerName());
		EasyMock.replay(behavior);

		PlayerLoginDataMapper gateway = new PlayerLoginDataMapper(behavior);
		gateway.find(PlayersForTest.MERLIN.getPlayerName());
		assertEquals(PlayersForTest.MERLIN.getPlayerPassword(), gateway.getPassword());
		assertEquals(PlayersForTest.MERLIN.getPlayerID(), gateway.getPlayerID());
		assertEquals(PlayersForTest.MERLIN.getPlayerName(), gateway.getPlayerName());
		EasyMock.verify(behavior);
	}
	
	/**
	 * All setters should defer to the data behavior
	 * @throws DatabaseException shouldnt
	 */
	@Test
	public void settersDefer() throws DatabaseException
	{
		PlayerLoginRowDataGateway gateway = EasyMock.createNiceMock(PlayerLoginRowDataGateway.class);
		gateway.find(PlayersForTest.ANDY.getPlayerName());
		gateway.setPassword("x");
		EasyMock.replay(gateway);

		PlayerLoginDataMapper mapper = new PlayerLoginDataMapper(gateway);
		mapper.find(PlayersForTest.ANDY.getPlayerName());
		mapper.setPassword("x");
		EasyMock.verify(gateway);
	}
}
