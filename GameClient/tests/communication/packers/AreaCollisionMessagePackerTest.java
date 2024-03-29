package communication.packers;

import static org.junit.Assert.assertEquals;
import model.reports.AreaCollisionReport;

import org.junit.Test;

import communication.messages.AreaCollisionMessage;

/**
 * @author Dave
 *
 * Make sure that the ChatMessagePacker behaves properly.
 */
public class AreaCollisionMessagePackerTest
{
	
	/**
	 * Make sure that the report is properly translated into the message.
	 */
	@Test
	public void testPacking()
	{
		int sender = 1;
		String region = "test";
		
		AreaCollisionReport report = new AreaCollisionReport(sender, region);
		AreaCollisionMessagePacker packer = new AreaCollisionMessagePacker();
		AreaCollisionMessage msg = (AreaCollisionMessage) packer.pack(report);
		
		assertEquals(sender, msg.getPlayerID());
		assertEquals(region, msg.getAreaName());
	}

}
