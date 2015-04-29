package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.AdventureRecord;
import edu.ship.shipsim.areaserver.datasource.AdventureStateRecord;
import edu.ship.shipsim.areaserver.datasource.AdventureStateTableDataGatewayRDS;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;

/**
 * @author Merlin
 *
 */
public class AdventureStateViewTableDataGatewayRDSTest extends DatabaseTest
{
	/**
	 * We should be able to get a list of Adventure Records for the adventures that a player has pending
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void getPendingAdventures() throws DatabaseException
	{
		List<AdventureRecord> results = AdventureStateViewTableDataGatewayRDS.getPendingAdventureRecords(PlayersForTest.JOHN.getPlayerID());
		assertEquals(2, results.size());
		AdventureRecord expected = new AdventureRecord(AdventuresForTest.FIVE.getQuestID(), AdventuresForTest.FIVE.getAdventureID(),
				AdventuresForTest.FIVE.getAdventureDescription(), AdventuresForTest.FIVE.getExperiencePointsGained(), AdventuresForTest.FIVE.getSignatureSpecification());
		assertTrue(results.contains(expected));
		assertTrue(results.contains(new AdventureRecord(AdventuresForTest.FOUR.getQuestID(), AdventuresForTest.FOUR.getAdventureID(),
				AdventuresForTest.FOUR.getAdventureDescription(), AdventuresForTest.FOUR.getExperiencePointsGained(), AdventuresForTest.FOUR.getSignatureSpecification())));
	}

	/**
	 * We should be able to get a list of Adventure Records for the adventures that a player has pending
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canCompleteAnAdventure() throws DatabaseException
	{
		List<AdventureRecord> initial = AdventureStateViewTableDataGatewayRDS.getPendingAdventureRecords(PlayersForTest.JOHN.getPlayerID());
		AdventureStateViewTableDataGatewayRDS.moveToNeedNotification(PlayersForTest.JOHN.getPlayerID(), initial.get(0).getQuestID(),initial.get(0).getAdventureID());
		ArrayList<AdventureStateRecord> all = AdventureStateTableDataGatewayRDS.getSingleton().getAdventureStates(PlayersForTest.JOHN.getPlayerID(), initial.get(0).getQuestID());
		for (AdventureStateRecord rec:all)
		{
			if (rec.getAdventureID() == initial.get(0).getAdventureID())
			{
				assertEquals(AdventureStateEnum.COMPLETED, rec.getState());
				assertTrue(rec.isNeedingNotification());
			}
		}
	}
}

