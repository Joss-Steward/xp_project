package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import datasource.DatabaseException;

/**
 * An abstract class that tests the table data gateways into the Adventure table
 * @author merlin
 *
 */
public abstract class AdventureTableDataGatewayTest
{

	/**
	 * @return the gateway we should test
	 */
	public abstract AdventureTableDataGateway getGateway();
	
	/**
	 * 
	 */
	@Test
	public void isASingleton()
	{
		AdventureTableDataGateway x = getGateway();
		AdventureTableDataGateway y = getGateway();
		assertSame(x,y);
		assertNotNull(x);
	}
	
	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveAllAdventuresForQuest() throws DatabaseException
	{
		AdventureTableDataGateway gateway = getGateway();
		ArrayList<AdventureRecord> records = gateway.getAdventuresForQuest(1);
		assertEquals(3, records.size());
		AdventureRecord record = records.get(0);
		// the records could be in either order
		if (record.getAdventureID() == AdventuresForTest.ONE.getAdventureID())
		{
			assertEquals(AdventuresForTest.ONE.getAdventureDescription(), record.getAdventureDescription());
			assertEquals(AdventuresForTest.ONE.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.ONE.getExperiencePointsGained(), record.getExperiencePointsGained());
			record = records.get(1);
			assertEquals(AdventuresForTest.TWO.getAdventureDescription(), record.getAdventureDescription());
			assertEquals(AdventuresForTest.TWO.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.TWO.getExperiencePointsGained(), record.getExperiencePointsGained());
		} else
		{
			assertEquals(AdventuresForTest.TWO.getAdventureID(), record.getAdventureID());
			assertEquals(AdventuresForTest.TWO.getAdventureDescription(), record.getAdventureDescription());
			assertEquals(AdventuresForTest.TWO.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.TWO.getExperiencePointsGained(), record.getExperiencePointsGained());
			record = records.get(1);
			assertEquals(AdventuresForTest.ONE.getAdventureDescription(), record.getAdventureDescription());
			assertEquals(AdventuresForTest.ONE.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.ONE.getExperiencePointsGained(), record.getExperiencePointsGained());
			
		}
	}
	

}
