package model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Merlin
 *
 */
public class MapToServerMappingTest
{
	/**
	 * Reset the database to have consistent data all of the time
	 * OptionsManager should not be in test mode, because we want to hit the actual database
	 * @throws SQLException shouldn't
	 */
	@Before
	public void resetMappingInDB() throws SQLException
	{
		MapToServerMapping reset = new MapToServerMapping();
		reset.setHostName(ServersInDB.FIRST_SERVER.getHostName());
		reset.setMapName(ServersInDB.FIRST_SERVER.getMapName());
		reset.setPortNumber(ServersInDB.FIRST_SERVER.getPortNumber());
		MapToServerMapping.getServerDao().update(reset);
		OptionsManager.resetSingleton();
	}

	/**
	 * Can retrieve one
	 * @throws SQLException  shouldn't
	 */
	@Test
	public void retrieval() throws SQLException
	{
		this.resetMappingInDB();
		ServersInDB expected = ServersInDB.FIRST_SERVER;
		MapToServerMapping map = MapToServerMapping.retrieveMapping(expected.getMapName());
		assertEquals(expected.getMapName(), map.getMapName());
		assertEquals(expected.getHostName(), map.getHostName());
		assertEquals(expected.getPortNumber(), map.getPortNumber());
	}
	
	/**
	 * Make sure we can change the hostname and port number and update the database appropriately
	 * @throws SQLException shouldn't
	 */
	@Test
	public void canPersistChanges() throws SQLException
	{
		MapToServerMapping map = MapToServerMapping.retrieveMapping(ServersInDB.FIRST_SERVER.getMapName());
		map.setHostName("homehost");
		map.setPortNumber(42);
		map.persist();
		
		MapToServerMapping mapAfter = MapToServerMapping.retrieveMapping(ServersInDB.FIRST_SERVER.getMapName());
		assertEquals(map,mapAfter);
		
		map.setHostName(ServersInDB.FIRST_SERVER.getHostName());
		map.setPortNumber(ServersInDB.FIRST_SERVER.getPortNumber());
		map.persist();
		
	}
	
	
	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(MapToServerMapping.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}
}
