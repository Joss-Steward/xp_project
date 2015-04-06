package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.AdventureRecord;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;

/**
 * @author Merlin
 *
 */
public class AdventureStateViewTableDataGatewayRDSTest
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
				AdventuresForTest.FIVE.getAdventureDescription());
		assertTrue(results.contains(expected));
		assertTrue(results.contains(new AdventureRecord(AdventuresForTest.FOUR.getQuestID(), AdventuresForTest.FOUR.getAdventureID(),
				AdventuresForTest.FOUR.getAdventureDescription())));
	}

}
