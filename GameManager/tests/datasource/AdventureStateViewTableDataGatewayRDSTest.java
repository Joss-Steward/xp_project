package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import testData.AdventuresForTest;
import testData.PlayersForTest;
import data.AdventureRecord;
import data.AdventureStateEnum;
import data.AdventureStateRecord;

/**
 * @author Merlin
 *
 */
public class AdventureStateViewTableDataGatewayRDSTest extends DatabaseTest
{
	/**
	 * We should be able to get a list of Adventure Records for the adventures
	 * that a player has pending
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void getPendingAdventures() throws DatabaseException
	{
		List<AdventureRecord> results = AdventureStateViewTableDataGatewayRDS
				.getPendingAdventureRecords(PlayersForTest.JOHN.getPlayerID());
		assertEquals(3, results.size());
		AdventureRecord expected = new AdventureRecord(
				AdventuresForTest.QUEST3_ADVENTURE1.getQuestID(),
				AdventuresForTest.QUEST3_ADVENTURE1.getAdventureID(),
				AdventuresForTest.QUEST3_ADVENTURE1.getAdventureDescription(),
				AdventuresForTest.QUEST3_ADVENTURE1.getExperiencePointsGained(),
				AdventuresForTest.QUEST3_ADVENTURE1.getCompletionType(),
				AdventuresForTest.QUEST3_ADVENTURE1.getCompletionCriteria());
		assertTrue(results.contains(expected));
		assertTrue(results.contains(new AdventureRecord(
				AdventuresForTest.QUEST3_ADVENTURE3.getQuestID(),
				AdventuresForTest.QUEST3_ADVENTURE3.getAdventureID(),
				AdventuresForTest.QUEST3_ADVENTURE3.getAdventureDescription(),
				AdventuresForTest.QUEST3_ADVENTURE3.getExperiencePointsGained(),
				AdventuresForTest.QUEST3_ADVENTURE3.getCompletionType(),
				AdventuresForTest.QUEST3_ADVENTURE3.getCompletionCriteria())));
		assertTrue(results.contains(new AdventureRecord(
				AdventuresForTest.QUEST2_ADVENTURE3.getQuestID(),
				AdventuresForTest.QUEST2_ADVENTURE3.getAdventureID(),
				AdventuresForTest.QUEST2_ADVENTURE3.getAdventureDescription(),
				AdventuresForTest.QUEST2_ADVENTURE3.getExperiencePointsGained(),
				AdventuresForTest.QUEST2_ADVENTURE3.getCompletionType(),
				AdventuresForTest.QUEST2_ADVENTURE3.getCompletionCriteria())));
	}

	/**
	 * We should be able to get a list of Adventure Records for the adventures
	 * that a player has pending
	 * 
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void canCompleteAnAdventure() throws DatabaseException
	{
		List<AdventureRecord> initial = AdventureStateViewTableDataGatewayRDS
				.getPendingAdventureRecords(PlayersForTest.JOHN.getPlayerID());
		AdventureStateViewTableDataGatewayRDS.moveToCompleted(PlayersForTest.JOHN
				.getPlayerID(), initial.get(0).getQuestID(), initial.get(0)
				.getAdventureID());
		ArrayList<AdventureStateRecord> all = AdventureStateTableDataGatewayRDS
				.getSingleton().getAdventureStates(PlayersForTest.JOHN.getPlayerID(),
						initial.get(0).getQuestID());
		for (AdventureStateRecord rec : all)
		{
			if (rec.getAdventureID() == initial.get(0).getAdventureID())
			{
				assertEquals(AdventureStateEnum.COMPLETED, rec.getState());
				assertTrue(rec.isNeedingNotification());
			}
		}
	}
}
