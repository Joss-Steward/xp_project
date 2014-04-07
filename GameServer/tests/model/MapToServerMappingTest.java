package model;

import static org.junit.Assert.*;

import java.sql.SQLException;

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

}
