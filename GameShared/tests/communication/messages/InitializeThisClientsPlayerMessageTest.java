package communication.messages;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import data.ClientPlayerAdventureState;
import data.ClientPlayerQuestState;
import datasource.LevelRecord;
import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;

/**
 * Tests the InitializeThisClientsPlayerMessage class
 *
 * @author Olivia, LaVonne
 *
 */
public class InitializeThisClientsPlayerMessageTest
{
	/**
	 * Tests that we can create InitializeThisClientsPlayerMessage and get the
	 * values of its fields
	 */
	@Test
	public void testInitialize()
	{
		ClientPlayerAdventureState adventureOne = new ClientPlayerAdventureState(1, "Test Adventure 1", 3,
				AdventureStateEnum.HIDDEN, false, true, "Dean", QuestStateEnum.AVAILABLE);
		ClientPlayerAdventureState adventureTwo = new ClientPlayerAdventureState(2, "Test Adventure 2", 3,
				AdventureStateEnum.HIDDEN, false, false, null, QuestStateEnum.AVAILABLE);
		ClientPlayerQuestState q = new ClientPlayerQuestState(1, "title", "Test Quest 1", QuestStateEnum.AVAILABLE, 42, 13, true,
				null);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		ArrayList<ClientPlayerQuestState> list = new ArrayList<ClientPlayerQuestState>();
		list.add(q);
		LevelRecord level = new LevelRecord("One", 15, 10, 7);
		InitializeThisClientsPlayerMessage message = new InitializeThisClientsPlayerMessage(list, 20, level);

		assertEquals(20, message.getExperiencePts());
		assertEquals("One", message.getLevel().getDescription());
		assertEquals(1, message.getClientPlayerQuestList().size());
		ClientPlayerQuestState firstQuest = message.getClientPlayerQuestList().get(0);
		assertEquals(42, firstQuest.getExperiencePointsGained());
		assertEquals(13, firstQuest.getAdventuresToFulfillment());
		assertTrue(firstQuest.isNeedingNotification());
		ClientPlayerAdventureState firstAdventure = firstQuest.getAdventureList().get(0);
		assertTrue(firstAdventure.isRealLifeAdventure());
		assertEquals("Dean", firstAdventure.getWitnessTitle());
	}
}
