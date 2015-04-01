package communication.messages;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;

import org.junit.Test;

import datasource.AdventureStateEnum;
import datasource.LevelRecord;
import datasource.QuestStateEnum;

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
		ClientPlayerAdventure adventureOne = new ClientPlayerAdventure(1, "Test Adventure 1", AdventureStateEnum.HIDDEN);
		ClientPlayerAdventure adventureTwo = new ClientPlayerAdventure(2, "Test Adventure 2", AdventureStateEnum.HIDDEN);
		ClientPlayerQuest q = new ClientPlayerQuest(1, "Test Quest 1", QuestStateEnum.HIDDEN);
		q.addAdventure(adventureOne);
		q.addAdventure(adventureTwo);
		ArrayList<ClientPlayerQuest> list = new ArrayList<ClientPlayerQuest>();
		list.add(q);
		LevelRecord level = new LevelRecord("One", 15);
		InitializeThisClientsPlayerMessage message = new InitializeThisClientsPlayerMessage(list, 20, level);
		
		assertEquals(20, message.getExperiencePts());
		assertEquals("One", message.getLevel().getDescription());
		assertEquals(1, message.getClientPlayerQuestList().size());
	}
}
