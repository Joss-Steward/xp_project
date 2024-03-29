package datasource;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import testData.AdventuresForTest;
import data.GameLocation;
import datasource.AdventureTableDataGateway;
import datasource.DatabaseException;
import datatypes.Position;
import model.AdventureRecord;

/**
 * An abstract class that tests the table data gateways into the Adventure table
 * 
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
		assertSame(x, y);
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
		if (record.getAdventureID() == AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID())
		{
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),
					record.getAdventureDescription());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getExperiencePointsGained(),
					record.getExperiencePointsGained());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getCompletionType(), record.getCompletionType());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getCompletionCriteria(), record.getCompletionCriteria());
			record = records.get(1);
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureDescription(),
					record.getAdventureDescription());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getExperiencePointsGained(),
					record.getExperiencePointsGained());
		} else
		{
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureID(), record.getAdventureID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureDescription(),
					record.getAdventureDescription());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getExperiencePointsGained(),
					record.getExperiencePointsGained());
			record = records.get(1);
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),
					record.getAdventureDescription());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), record.getQuestID());
			assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getExperiencePointsGained(),
					record.getExperiencePointsGained());

		}
	}

	/**
	 * We should be able to retrieve the specific information about one single
	 * adventure
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canGetSingleAdventure() throws DatabaseException
	{
		AdventureTableDataGateway gateway = getGateway();
		AdventureRecord record = gateway.getAdventure(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(),
				AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(), record.getAdventureDescription());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureID(), record.getAdventureID());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getExperiencePointsGained(),
				record.getExperiencePointsGained());
		assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), record.getQuestID());
	}

	/**
	 * We should be able to retrieve the specific information about one single
	 * adventure
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void nullForMissingAdventure() throws DatabaseException
	{
		AdventureTableDataGateway gateway = getGateway();
		AdventureRecord record = gateway.getAdventure(42, 16);
		assertNull(record);
	}

	/**
	 * We should be able to receive a list of all quests completed at a location
	 * 
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canGetCompleteByLocationAdventures() throws DatabaseException
	{
		GameLocation location = (GameLocation) (AdventuresForTest.QUEST2_ADVENTURE2.getCompletionCriteria());
		String mapName = location.getMapName();
		Position pos = location.getPosition();
		// System.out.println(mapName);
		// System.out.println(pos.getRow() + ", " + pos.getColumn());
		AdventureTableDataGateway gateway = getGateway();
		ArrayList<AdventuresForTest> adventure = new ArrayList<AdventuresForTest>();
		adventure.add(AdventuresForTest.QUEST2_ADVENTURE2);
		ArrayList<AdventureRecord> adventuresFound = gateway.findAdventuresCompletedForMapLocation(mapName, pos);
		assertEquals(adventure.get(0).getAdventureDescription(), adventuresFound.get(0).getAdventureDescription());
	}

}
