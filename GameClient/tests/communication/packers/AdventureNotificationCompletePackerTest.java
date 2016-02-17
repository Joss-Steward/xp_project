package communication.packers;

import static org.junit.Assert.*;
import model.reports.AdventureNotifcationCompleteReport;

import org.junit.Test;

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
		
		assertEquals(AdventureNotifcationCompleteReport.class, packer.getReportTypesWePack());
	}

}
