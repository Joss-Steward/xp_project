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
public abstract class AdventureStateTableDataGatewayTest
{

	/**
	 * @return the gateway we should test
	 */
	public abstract AdventureStateTableDataGateway getGateway();
	
	/**
	 * 
	 */
	@Test
	public void isASingleton()
	{
		AdventureStateTableDataGateway x = getGateway();
		AdventureStateTableDataGateway y = getGateway();
		assertSame(x,y);
		assertNotNull(x);
	}
	
	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveAllAdventuresForQuest() throws DatabaseException
	{
		AdventureStateTableDataGateway gateway = getGateway();
		ArrayList<AdventureStateRecord> records = gateway.getAdventureStates(2,1);
		assertEquals(2, records.size());
		AdventureStateRecord record = records.get(0);
		// the records could be in either order
		AdventureStatesForTest first = AdventureStatesForTest.PLAYER2_QUEST1_ADV1;
		AdventureStatesForTest other = AdventureStatesForTest.PLAYER2_QUEST1_ADV2;
		if (record.getAdventureID() == first.getAdventureID())
		{
			assertEquals(first.getState(), record.getState());
			assertEquals(first.getQuestID(), record.getQuestID());
			record = records.get(1);
			assertEquals(other.getState(), record.getState());
			assertEquals(other.getQuestID(), record.getQuestID());
		} else
		{
			assertEquals(other.getAdventureID(), record.getAdventureID());
			assertEquals(other.getState(), record.getState());
			assertEquals(other.getQuestID(), record.getQuestID());
			record = records.get(1);
			assertEquals(first.getState(), record.getState());
			assertEquals(first.getQuestID(), record.getQuestID());
		}
	}
	

}
