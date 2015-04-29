package edu.ship.shipsim.areaserver.datasource;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import datasource.AdventureStateEnum;
import datasource.DatabaseException;
import datasource.DatabaseTest;

/**
 * An abstract class that tests the table data gateways into the Adventure table
 * 
 * @author merlin
 *
 */
public abstract class AdventureStateTableDataGatewayTest extends DatabaseTest
{

	private AdventureStateTableDataGateway gateway;

	@Before
	public void setup() throws DatabaseException
	{
		super.setUp();
		this.getGateway().resetData();
	}
	/**
	 * Make sure any static information is cleaned up between tests
	 * @throws SQLException shouldn't
	 * @throws DatabaseException shouldn't
	 */
	@After
	public void cleanup() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}
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
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * Make sure we can retrieve all pending adventures for a given player
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void retrieveAllPendingAdventuresForPlayer() throws DatabaseException
	{
		 gateway = getGateway();
		ArrayList<AdventureStateRecord> records = gateway
				.getPendingAdventuresForPlayer(1);
		assertEquals(2, records.size());
		AdventureStateRecord record = records.get(0);
		// the records could be in either order
		AdventureStatesForTest first = AdventureStatesForTest.PLAYER1_QUEST2_ADV2;
		AdventureStatesForTest other = AdventureStatesForTest.PLAYER1_QUEST3_ADV1;
		if (record.getAdventureID() == first.getAdventureID())
		{
			assertEquals(first.getState(), record.getState());
			assertEquals(first.getQuestID(), record.getQuestID());
			assertEquals(first.isNeedingNotification(), record.isNeedingNotification());
			record = records.get(1);
			assertEquals(other.getState(), record.getState());
			assertEquals(other.getQuestID(), record.getQuestID());
			assertEquals(other.isNeedingNotification(), record.isNeedingNotification());
			
		} else
		{
			assertEquals(other.getAdventureID(), record.getAdventureID());
			assertEquals(other.getState(), record.getState());
			assertEquals(other.getQuestID(), record.getQuestID());
			assertEquals(other.isNeedingNotification(), record.isNeedingNotification());
			
			record = records.get(1);
			assertEquals(first.getState(), record.getState());
			assertEquals(first.getQuestID(), record.getQuestID());
			assertEquals(first.isNeedingNotification(), record.isNeedingNotification());
			
		}
	}

	/**
	 * Make sure that nothing goes wrong if we try to retrieve pending
	 * adventures and there aren't any
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void retrievePendingAdventuresForPlayerWithNone() throws DatabaseException
	{
		gateway = getGateway();
		ArrayList<AdventureStateRecord> records = gateway
				.getPendingAdventuresForPlayer(3);
		assertEquals(0, records.size());
	}

	/**
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void retrieveAllAdventuresForQuest() throws DatabaseException
	{
		gateway = getGateway();
		ArrayList<AdventureStateRecord> records = gateway.getAdventureStates(1, 2);
		assertEquals(2, records.size());
		AdventureStateRecord record = records.get(0);
		// the records could be in either order
		AdventureStatesForTest first = AdventureStatesForTest.PLAYER1_QUEST2_ADV1;
		AdventureStatesForTest other = AdventureStatesForTest.PLAYER1_QUEST2_ADV2;
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

	/**
	 * If a quest has no quests, we should return an empty list
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void returnsEmptyListIfNone() throws DatabaseException
	{
		gateway = getGateway();
		ArrayList<AdventureStateRecord> actual = gateway.getAdventureStates(109, 1);
		assertEquals(0, actual.size());
	}

	/**
	 * Should be able to change the state of an existing record
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void canChangeExisting() throws DatabaseException
	{
		gateway = getGateway();
		AdventureStatesForTest adv = AdventureStatesForTest.PLAYER2_QUEST1_ADV3;
		gateway.updateState(adv.getPlayerID(), adv.getQuestID(), adv.getAdventureID(),
				AdventureStateEnum.COMPLETED);

		ArrayList<AdventureStateRecord> actual = gateway.getAdventureStates(
				adv.getPlayerID(), adv.getQuestID());
		int count = 0;
		for (AdventureStateRecord asRec : actual)
		{
			if (asRec.getQuestID() == adv.getQuestID()
					&& asRec.getAdventureID() == adv.getAdventureID())
			{
				count = count + 1;
				assertEquals(AdventureStateEnum.COMPLETED, asRec.getState());
				assertTrue(asRec.isNeedingNotification());
			}
		}
		assertEquals(1, count);
	}

	/**
	 * Updating a non-existing record adds it to the data source
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void canAddNew() throws DatabaseException
	{
		gateway = getGateway();
		gateway.updateState(13, 22, 11, AdventureStateEnum.PENDING);
		ArrayList<AdventureStateRecord> actual = gateway.getAdventureStates(13, 22);
		assertEquals(1, actual.size());
		AdventureStateRecord asRec = actual.get(0);
		assertEquals(13, asRec.getPlayerID());
		assertEquals(22, asRec.getQuestID());
		assertEquals(11, asRec.getAdventureID());
		assertEquals(AdventureStateEnum.PENDING, asRec.getState());
		assertTrue(asRec.isNeedingNotification());

	}
}
