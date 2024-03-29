package model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.CommandAdventureStateChange;
import model.ClientPlayerManager;

import org.junit.Test;

import communication.messages.AdventureStateChangeMessage;
import data.ClientPlayerAdventureState;
import data.ClientPlayerQuestState;
import datatypes.AdventureStateEnum;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import datatypes.QuestStateEnum;

/**
 * @author sl6469
 *
 */

public class CommandAdventureStateChangeTest
{

	/**
	 * test the setup of the constructor
	 */
	@Test
	public void testInitialization()
	{
		CommandAdventureStateChange x = new CommandAdventureStateChange(
				new AdventureStateChangeMessage(1, 2, 5, "Silly Adventure",
						AdventureStateEnum.COMPLETED, true, "Lex Luther"));
		assertEquals(2, x.getQuestID());
		assertEquals(5, x.getAdventureID());
		assertEquals("Silly Adventure", x.getAdventureDescription());
		assertEquals(AdventureStateEnum.COMPLETED, x.getAdventureState());
		assertTrue(x.isRealLifeAdventure());
		assertEquals("Lex Luther", x.getWitnessTitle());
	}

	/**
	 * Test that when we execute the CommandQuestStateChange ThisClientsPlayer
	 * ClientPlayerQuest's state changes
	 * 
	 * @throws AlreadyBoundException
	 *             shouldn't
	 * @throws NotBoundException
	 *             shouldn't
	 */
	@Test
	public void testChangingAdventure() throws AlreadyBoundException, NotBoundException
	{

		int playerID = 1;
		int questID = 1;
		int adventureID = 1;

		ClientPlayerQuestState q = new ClientPlayerQuestState(questID, "questtitle", "silly quest",
				QuestStateEnum.TRIGGERED, 3, 0, false, null);
		ClientPlayerAdventureState a1 = new ClientPlayerAdventureState(adventureID, "adventure 1",
				3, AdventureStateEnum.TRIGGERED, false, true, "Comrade", QuestStateEnum.AVAILABLE);
		q.getAdventureList().add(a1);

		Position pos = new Position(1, 2);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		pm.initializePlayer(playerID, "Player 1", "Player 1 Type", pos, Crew.NULL_POINTER, Major.COMPUTER_ENGINEERING);

		pm.initiateLogin("john", "pw");
		pm.finishLogin(playerID);

		ClientPlayerManager.getSingleton().getThisClientsPlayer().addQuest(q);

		CommandAdventureStateChange x = new CommandAdventureStateChange(
				new AdventureStateChangeMessage(playerID, questID, adventureID,
						"adventure 1", AdventureStateEnum.COMPLETED, true, "Comrade"));
		x.execute();

		for (ClientPlayerQuestState quest : ClientPlayerManager.getSingleton()
				.getThisClientsPlayer().getQuests())
		{
			if (quest.getQuestID() == questID)
			{
				for (ClientPlayerAdventureState adventure : quest.getAdventureList())
				{
					if (adventure.getAdventureID() == adventureID)
					{
						assertEquals(AdventureStateEnum.COMPLETED,
								adventure.getAdventureState());
					}
				}
			}
		}
	}

}
