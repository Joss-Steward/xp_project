package edu.ship.shipsim.areaserver.model;

import static org.junit.Assert.*;
import model.MapToServerMapping;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.ServersForTest;

/**
 * Tests the OptionsManager
 * 
 * @author Steve
 *
 */
public class OptionsManagerTest
{

	/**
	 * Reset that singleton
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Before
	public void setup() throws DatabaseException
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton(true);
		MapToServerMapping mapping = new MapToServerMapping(
				ServersForTest.FIRST_SERVER.getMapName());
		mapping.setHostName("holder");
		mapping.setMapName(ServersForTest.FIRST_SERVER.getMapName());
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
		assertNotSame(pm1, OptionsManager.getSingleton(true));
	}

	/**
	 * When we set the map name, the map to server mapping is updated in the
	 * database
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void savesServerMapping() throws DatabaseException
	{
		OptionsManager manager = OptionsManager.getSingleton(true);
		manager.updateMapInformation(ServersForTest.FIRST_SERVER.getMapName(),
				"ourhost.com", 1337);

		MapToServerMapping expected = new MapToServerMapping();
		expected.setHostName("ourhost.com");
		expected.setMapName(ServersForTest.FIRST_SERVER.getMapName());
		expected.setPortNumber(1337);

		MapToServerMapping actual = new MapToServerMapping(
				ServersForTest.FIRST_SERVER.getMapName());
		assertEquals(expected, actual);
	}

	/**
	 * Basic getter test
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void serverMappingGetters() throws DatabaseException
	{
		OptionsManager manager = OptionsManager.getSingleton();
		manager.updateMapInformation(ServersForTest.FIRST_SERVER.getMapName(),
				"ourhost.com", 1337);

		assertEquals(ServersForTest.FIRST_SERVER.getMapName(), manager.getMapName());
		assertEquals("ourhost.com", manager.getHostName());
		assertEquals(1337, manager.getPortNumber());
	}
}
