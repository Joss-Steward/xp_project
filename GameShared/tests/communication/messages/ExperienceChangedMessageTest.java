package communication.messages;

import static org.junit.Assert.*;

import org.junit.Test;

import testData.PlayersForTest;
import datasource.LevelRecord;

/**
 * Tests that the ExperienceChangeMessage class functionality
 * @author Olivia
 * @author LaVonne
 */
public class ExperienceChangedMessageTest 
{

	/**
	 * Tests that we can create ExperienceChangeMessage and sets its fields
	 */
	@Test
	public void testCreateMessage() 
	{
		LevelRecord record = new LevelRecord("Serf", 15);
		ExperienceChangedMessage msg = new ExperienceChangedMessage(PlayersForTest.JOHN.getPlayerID(),PlayersForTest.JOHN.getExperiencePoints(), record);
		assertEquals(PlayersForTest.JOHN.getExperiencePoints(), msg.getExperiencePoints());
	}
}
