package model;

import static org.junit.Assert.*;

import java.sql.SQLException;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

/**
 * 
 * @author Merlin
 *
 */
public class MapToServerMappingTest
{

	/**
	 * Can retrieve one
	 * @throws SQLException  shouldn't
	 */
	@Test
	public void retrieval() throws SQLException
	{
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
