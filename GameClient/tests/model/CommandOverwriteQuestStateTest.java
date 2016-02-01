package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerQuest;
import model.CommandOverwriteQuestState;
import model.ClientPlayerManager;
import model.ThisClientsPlayer;

import org.junit.Test;

import communication.messages.InitializeThisClientsPlayerMessage;
import datasource.LevelRecord;
import datasource.PlayersForTest;
import datasource.QuestStateEnum;

/**
 * Test the Command to overwrite this client player quest list
 * @author Ryan
 *
 */
public class CommandOverwriteQuestStateTest
{
	/**
	 * Test the constructor of CommandOverwriteQuestState
	 */
	@Test
	public void constructor()
	{
		ArrayList<ClientPlayerQuest> expected = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(42, "silly", QuestStateEnum.AVAILABLE, 42, 2);
		expected.add(q);
		LevelRecord level = new LevelRecord("One", 15);
		
		CommandOverwriteQuestState x = new CommandOverwriteQuestState(new InitializeThisClientsPlayerMessage(expected, 20, level));
		assertEquals(expected, x.getClientPlayerQuestList());
	}
	
	/**
	 * Test the execute of the command and make sure that this client
	 * player quest list is overwritten
	 */
	@Test
	public void testExecutes()
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		PlayersForTest john = PlayersForTest.JOHN;
		pm.initiateLogin(john.getPlayerName(), john.getPlayerPassword());
		
		@SuppressWarnings("unused")
		ThisClientsPlayer cp = null;
		
		try
		{
			cp = pm.finishLogin(john.getPlayerID());
		} catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("Could not create this client's player from login");
		}
		
		ArrayList<ClientPlayerQuest> expected = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(42, "silly", QuestStateEnum.AVAILABLE, 22, 13);
		expected.add(q);
		LevelRecord level = new LevelRecord("One", 15);
		
		CommandOverwriteQuestState x = new CommandOverwriteQuestState(new InitializeThisClientsPlayerMessage(expected, 20, level));
		x.execute();
		
		ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		assertEquals(expected, player.getQuests());
	}

}
