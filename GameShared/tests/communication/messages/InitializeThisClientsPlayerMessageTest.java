package communication.messages;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;

import org.junit.Test;

import data.AdventureStateEnum;
import data.QuestStateEnum;
import datasource.LevelRecord;

/**
 * Tests the InitializeThisClientsPlayerMessage class
 * 
 * @author Olivia, LaVonne
 *
 */
public class InitializeThisClientsPlayerMessageTest 
{
	/**
	 * Tests that we can create InitializeThisClientsPlayerMessage
	 * and get the values of its fields
	 */
	@Test
	public void testInitialize() 
	{
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", 3, AdventureStateEnum.HIDDEN, false, true, "Dean");
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", 3, AdventureStateEnum.HIDDEN, false, false, null);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "title", "Test Quest 1", QuestStateEnum.HIDDEN, 42, 13, true);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		ArrayList<ClientPlayerQuest> list = new ArrayList<ClientPlayerQuest>();
		list.add(q);
		LevelRecord level = new LevelRecord("One", 15, 10, 7);
		InitializeThisClientsPlayerMessage message = new InitializeThisClientsPlayerMessage(list, 20, level);
		
		assertEquals(20, message.getExperiencePts());
		assertEquals("One", message.getLevel().getDescription());
		assertEquals(1, message.getClientPlayerQuestList().size());
		ClientPlayerQuest firstQuest = message.getClientPlayerQuestList().get(0);
		assertEquals(42, firstQuest.getExperiencePointsGained());
		assertEquals(13, firstQuest.getAdventuresToFulfillment());
		assertTrue(firstQuest.isNeedingNotification());
		ClientPlayerAdventure firstAdventure = firstQuest.getAdventureList().get(0);
		assertTrue(firstAdventure.isRealLifeAdventure());
		assertEquals("Dean", firstAdventure.getWitnessTitle());
	}
}
