package communication.packers;

import static org.junit.Assert.assertEquals;
import model.OptionsManager;

import org.junit.Before;
import org.junit.Test;

import communication.messages.ExperienceChangedMessage;
import datasource.DatabaseException;
import datasource.PlayersForTest;
import edu.ship.shipsim.areaserver.model.LevelManager;
import edu.ship.shipsim.areaserver.model.QuestManager;
import edu.ship.shipsim.areaserver.model.reports.ExperienceChangedReport;

/**
 * @author Ryan
 *
 * Make sure that the ExperienceChangedMessagePacker behaves properly.
 */
public class ExperienceChangedMessagePackerTest
{
	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton(true);
		QuestManager.resetSingleton();
		//TODO - Need test mode of Level Manager : LevelManager.getSingleton(true);
	}
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
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testPacking() throws DatabaseException
	{		
		ExperienceChangedReport report = new ExperienceChangedReport(PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getExperiencePoints(), LevelManager.getSingleton().getLevelForPoints(PlayersForTest.JOHN.getExperiencePoints()));
		ExperienceChangedMessagePacker packer = new ExperienceChangedMessagePacker();
		ExperienceChangedMessage msg = (ExperienceChangedMessage) packer.pack(report);
		
		
		assertEquals(PlayersForTest.JOHN.getExperiencePoints(), msg.getExperiencePoints());
	}
	
	

}
