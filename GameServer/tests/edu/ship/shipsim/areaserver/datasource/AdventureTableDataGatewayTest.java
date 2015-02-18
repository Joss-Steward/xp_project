package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public abstract class AdventureTableDataGatewayTest
{

	public abstract AdventureTableDataGateway getGateway();
	
	@Test
	public void isASingleton()
	{
		AdventureTableDataGateway x = getGateway();
		AdventureTableDataGateway y = getGateway();
		assertSame(x,y);
		assertNotNull(x);
	}
	
	@Test
	public void retrieveAllAdventuresForQuest()
	{
		AdventureTableDataGateway gateway = getGateway();
		ArrayList<AdventureRecord> records = gateway.getAdventuresForQuest(1);
		assertEquals(2, records.size());
		AdventureRecord record = records.get(0);
		// the records could be in either order
		if (record.getAdventureID() == AdventuresForTest.ONE.getAdventureID())
		{
			assertEquals(AdventuresForTest.ONE.getAdventureDescription(), record.getAdventureDescription());
			assertEquals(AdventuresForTest.ONE.getQuestID(), record.getQuestID());
			record = records.get(1);
			assertEquals(AdventuresForTest.TWO.getAdventureDescription(), record.getAdventureDescription());
			assertEquals(AdventuresForTest.TWO.getQuestID(), record.getQuestID());
		} else
		{
			assertEquals(AdventuresForTest.TWO.getAdventureID(), record.getAdventureID());
			assertEquals(AdventuresForTest.TWO.getAdventureDescription(), record.getAdventureDescription());
			assertEquals(AdventuresForTest.TWO.getQuestID(), record.getQuestID());
			record = records.get(1);
			assertEquals(AdventuresForTest.ONE.getAdventureDescription(), record.getAdventureDescription());
			assertEquals(AdventuresForTest.ONE.getQuestID(), record.getQuestID());
		}
	}
	

}
