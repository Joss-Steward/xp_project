package model;

import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ClientPlayerQuest;
import model.CommandQuestStateChange;
import model.ClientPlayerManager;

import org.junit.Test;

import communication.messages.QuestStateChangeMessage;
import data.Position;
import datasource.QuestStateEnum;

/**
 * @author Ryan
 *
 */
public class CommandQuestStateChangeTest 
{

	/**
	 * Test the constructor of CommandOverwriteQuestState
	 */
	@Test
	public void testConstructor()
	{
		CommandQuestStateChange x = new CommandQuestStateChange(new QuestStateChangeMessage(1,2, "Silly Quest", QuestStateEnum.FINISHED));
		assertEquals(2, x.getQuestID());
		assertEquals("Silly Quest", x.getQuestDescription());
		assertEquals(QuestStateEnum.FINISHED, x.getQuestState());
	}
	
	/**
	 * Test that when we execute the CommandQuestStateChange
	 * ThisClientsPlayer ClientPlayerQuest's state changes
	 * @throws AlreadyBoundException shouldn't
	 * @throws NotBoundException shouldn't
	 */
	@Test
	public void testChangingQuest() throws AlreadyBoundException, NotBoundException
	{
		int playerID = 1;
		int questID = 1;
		ClientPlayerQuest q = new ClientPlayerQuest(questID, "silly quest", QuestStateEnum.TRIGGERED, 3, 0);
		
		Position pos = new Position(1, 2);
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		pm.initializePlayer(playerID, "Player 1", "Player 1 Type", pos);

		pm.initiateLogin("john", "pw");
		pm.finishLogin(playerID);

		ClientPlayerManager.getSingleton().getThisClientsPlayer().addQuest(q);
		
		CommandQuestStateChange x = new CommandQuestStateChange(new QuestStateChangeMessage(playerID, questID, "silly quest", QuestStateEnum.FINISHED));
		x.execute();
		
		for(ClientPlayerQuest quest : ClientPlayerManager.getSingleton().getThisClientsPlayer().getQuests())
		{
			if(quest.getQuestID() == questID)
			{
				assertEquals(QuestStateEnum.FINISHED, quest.getQuestState());
			}
		}
	}

}
