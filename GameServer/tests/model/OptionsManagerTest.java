package model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the OptionsManager
 * @author Steve
 *
 */
public class OptionsManagerTest 
{

	/**
	 * Reset that singleton
	 * @throws SQLException won't
	 */
	@Before
	public void setup() throws SQLException
	{
		OptionsManager.resetSingleton();
		MapToServerMapping mapping = MapToServerMapping.retrieveMapping(ServersInDB.FIRST_SERVER.getMapName());
		mapping.setHostName("holder");
		mapping.setMapName(ServersInDB.FIRST_SERVER.getMapName());
		mapping.setPortNumber(0);
		mapping.persist();
	}
	
	/**
	 * Make sure OptionsManager is a resetable singleton
	 */
	@Test
	public void isSingleton()
	{
		OptionsManager pm1 = OptionsManager.getSingleton();
		OptionsManager pm2 = OptionsManager.getSingleton();
		assertSame(pm1, pm2);
		OptionsManager.resetSingleton();
		assertNotSame(pm1, OptionsManager.getSingleton());
	}
	
	/**
	 * When we set the map name, the map to server mapping is updated in the database
	 * @throws SQLException shouldn't
	 */
	@Test
	public void savesServerMapping() throws SQLException
	{
		OptionsManager manager = OptionsManager.getSingleton();
		manager.updateMapInformation(ServersInDB.FIRST_SERVER.getMapName(), "ourhost.com", 1337);
		
		MapToServerMapping expected = new MapToServerMapping();
		expected.setHostName("ourhost.com");
		expected.setMapName(ServersInDB.FIRST_SERVER.getMapName());
		expected.setPortNumber(1337);
		
		MapToServerMapping actual = MapToServerMapping.retrieveMapping(ServersInDB.FIRST_SERVER.getMapName());
		assertEquals(expected, actual);
	}
	
	/**
	 * Basic getter test
	 * @throws SQLException shouldn't
	 */
	@Test
	public void serverMappingGetters() throws SQLException
	{
		OptionsManager manager = OptionsManager.getSingleton();
		manager.updateMapInformation(ServersInDB.FIRST_SERVER.getMapName(), "ourhost.com", 1337);
		
		assertEquals(ServersInDB.FIRST_SERVER.getMapName(), manager.getMapName());
		assertEquals("ourhost.com", manager.getHostName());
		assertEquals(1337, manager.getPortNumber());
	}
}
