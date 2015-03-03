package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.QuestStateList;

/**
 * An abstract class that tests the table data gateways into the Adventure table
 * 
 * @author merlin
 *
 */
public abstract class QuestStateTableDataGatewayTest extends DatabaseTest
{

	/**
	 * @return the gateway we should test
	 */
	public abstract QuestStateTableDataGateway getGatewaySingleton();

	/**
	 * 
	 */
	@Test
	public void isASingleton()
	{
		QuestStateTableDataGateway x = getGatewaySingleton();
		QuestStateTableDataGateway y = getGatewaySingleton();
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void retrieveAllAdventuresForQuest() throws DatabaseException
	{
		QuestStateTableDataGateway gateway = getGatewaySingleton();
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

	/**
	 * We should be able to add new rows to the table
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void canInsertARecord() throws DatabaseException
	{
		QuestStateTableDataGateway gateway = getGatewaySingleton();
		gateway.createRow(QuestStatesForTest.PLAYER1_QUEST1.getPlayerID(), 3,
				QuestStateList.TRIGGERED);
		ArrayList<QuestStateRecord> actual = gateway
				.getQuestStates(QuestStatesForTest.PLAYER1_QUEST1.getPlayerID());
		assertEquals(3, actual.size());
		assertTrue(actual.contains(new QuestStateRecord(QuestStatesForTest.PLAYER1_QUEST1
				.getPlayerID(), 3, QuestStateList.TRIGGERED)));

	}

	/**
	 * The combination of player ID and quest ID should be unique in the table.
	 * If we try to add a duplicate, we should get a database exception
	 * 
	 * @throws DatabaseException should!
	 */
	@Test(expected = DatabaseException.class)
	public void cannotInsertDuplicateData() throws DatabaseException
	{
		QuestStateTableDataGateway gateway = getGatewaySingleton();
		gateway.createRow(QuestStatesForTest.PLAYER1_QUEST1.getPlayerID(),
				QuestStatesForTest.PLAYER1_QUEST1.getQuestID(), QuestStateList.TRIGGERED);
	}

}
