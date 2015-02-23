package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests a simple data transfer object that contains the information about one adventure
 * @author merlin
 *
 */
public class AdventureRecordTest
{
	/**
	 * Just make sure it holds and returns everything
	 */
	@Test
	public void constructAnAdventureRecord()
	{
		AdventureRecord record = new AdventureRecord (1, "Adventure Description 1", 1);
		assertEquals(1, record.getAdventureID());
		assertEquals("Adventure Description 1", record.getAdventureDescription());
		assertEquals(1, record.getQuestID());
	}
}
