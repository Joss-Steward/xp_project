package model.reports;

import static org.junit.Assert.*;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import testData.AdventuresForTest;
import testData.MapAreasForTest;
import data.AdventureStateEnum;

/**
 * Test to make sure the TeleportOnQuestFinishReport works
 * @author Chris Hersh, Zach Thompson, Abdul
 *
 */
public class TeleportOnQuestCompletionReportTest
{

    /** 
     * Tests the creation of the TeleportOnQuestFinishReport 
     */
    @Test
    public void testInitialization() 
    {
        String host = "hostname";
        int port = 22;
        TeleportOnQuestCompletionReport report = new TeleportOnQuestCompletionReport(1, AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), MapAreasForTest.ONE_MAP_AREA.getAreaName(),
                host, port);
        
        assertEquals(1, report.getPlayerID());
        assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getQuestID(), report.getQuestID());
        assertEquals(MapAreasForTest.ONE_MAP_AREA.getAreaName(), report.getMapName());
        assertEquals(host, report.getHostName());
        assertEquals(port, report.getPortNumber());
    }


}
