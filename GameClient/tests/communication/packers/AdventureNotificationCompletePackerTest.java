package communication.packers;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ship.shipsim.client.model.reports.AdventureNotifcationCompleteReport;

/**
 * Test for AdventureNotificationCompletePacker
 * @author Ryan
 *
 */
public class AdventureNotificationCompletePackerTest 
{
	
	/**
	 * Test report type we pack
	 */
	@Test
	public void testReportTypeWePack() 
	{
		AdventureNotificationCompletePacker packer = new AdventureNotificationCompletePacker();
		
		assertEquals(AdventureNotifcationCompleteReport.class, packer.getReportTypeWePack());
	}

}
