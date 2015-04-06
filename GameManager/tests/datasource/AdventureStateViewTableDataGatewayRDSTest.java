package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.datasource.AdventureRecord;
import edu.ship.shipsim.areaserver.datasource.AdventuresForTest;

public class AdventureStateViewTableDataGatewayRDSTest
{
	
	@Test
	public void getPendingAdventures() throws DatabaseException
	{
		AdventureStateViewTableDataGatewayRDS gateway = new AdventureStateViewTableDataGatewayRDS(PlayersForTest.JOHN.getPlayerID());
		List<AdventureRecord> results = gateway.getPendingAdventureRecords();
		assertEquals(2, results.size());
		AdventureRecord expected = new AdventureRecord(AdventuresForTest.FIVE.getQuestID(), AdventuresForTest.FIVE.getAdventureID(),
				AdventuresForTest.FIVE.getAdventureDescription());
		assertTrue(results.contains(expected));
		assertTrue(results.contains(new AdventureRecord(AdventuresForTest.FOUR.getQuestID(), AdventuresForTest.FOUR.getAdventureID(),
				AdventuresForTest.FOUR.getAdventureDescription())));
	}

}
