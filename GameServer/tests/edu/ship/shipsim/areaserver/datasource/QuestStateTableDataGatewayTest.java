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
public abstract class QuestStateTableDataGatewayTest
{

	/**
	 * @return the gateway we should test
	 */
	public abstract QuestStateTableDataGateway getGateway();
	
	/**
	 * 
	 */
	@Test
	public void isASingleton()
	{
		QuestStateTableDataGateway x = getGateway();
		QuestStateTableDataGateway y = getGateway();
		assertSame(x,y);
		assertNotNull(x);
	}
	
	/**
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrieveAllAdventuresForQuest() throws DatabaseException
	{
		QuestStateTableDataGateway gateway = getGateway();
		ArrayList<QuestStateRecord> records = gateway.getQuestStates(1);
		assertEquals(2, records.size());
		QuestStateRecord record = records.get(0);
		// the records could be in either order
		QuestStatesForTest first = QuestStatesForTest.PLAYER1_QUEST1;
		QuestStatesForTest other = QuestStatesForTest.PLAYER1_QUEST2;
		if (record.getQuestID() == first.getQuestID())
		{
			assertEquals(first.getState(), record.getState());
			record = records.get(1);
			assertEquals(other.getState(), record.getState());
			assertEquals(other.getQuestID(), record.getQuestID());
		} else
		{
			assertEquals(other.getState(), record.getState());
			assertEquals(other.getQuestID(), record.getQuestID());
			record = records.get(1);
			assertEquals(first.getState(), record.getState());
			assertEquals(first.getQuestID(), record.getQuestID());
		}
	}
	

}
