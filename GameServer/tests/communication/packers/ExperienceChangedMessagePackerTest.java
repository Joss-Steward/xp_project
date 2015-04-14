package communication.packers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import communication.messages.ExperienceChangedMessage;

import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.model.reports.ExperienceChangedReport;

/**
 * @author Dave
 *
 * Make sure that the ChatMessagePacker behaves properly.
 */
public class ExperienceChangedMessagePackerTest
{
	
	/**
	 * 
	 */
	@Test
	public void testReportWePack()
	{
		ExperienceChangedMessagePacker packer = new ExperienceChangedMessagePacker();
		
		assertEquals(ExperienceChangedReport.class, packer.getReportTypeWePack());
	}
	
	/**
	 * Make sure that the report is properly translated into the message.
	 */
	@Test
	public void testPacking()
	{		
		ExperienceChangedReport report = new ExperienceChangedReport(PlayersForTest.JOHN.getExperiencePoints(), PlayersForTest.JOHN.getPlayerID());
		ExperienceChangedMessagePacker packer = new ExperienceChangedMessagePacker();
		ExperienceChangedMessage msg = (ExperienceChangedMessage) packer.pack(report);
		
		
		assertEquals(PlayersForTest.JOHN.getExperiencePoints(), msg.getExperiencePoints());
		assertEquals(PlayersForTest.JOHN.getPlayerID(), msg.getPlayerID());
	}
	
	

}
