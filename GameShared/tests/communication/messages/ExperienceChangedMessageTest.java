package communication.messages;

import static org.junit.Assert.*;

import org.junit.Test;

import datasource.PlayersForTest;

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
		ExperienceChangedMessage msg = new ExperienceChangedMessage(PlayersForTest.JOHN.getExperiencePoints(), PlayersForTest.JOHN.getPlayerID());
		assertEquals(PlayersForTest.JOHN.getExperiencePoints(), msg.getExperiencePoints());
		assertEquals(PlayersForTest.JOHN.getPlayerID(), msg.getPlayerID());
	}
}
