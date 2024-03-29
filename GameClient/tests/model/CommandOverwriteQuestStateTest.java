package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.CommandOverwriteQuestState;
import model.ClientPlayerManager;
import model.ThisClientsPlayer;

import org.junit.Test;

import testData.PlayersForTest;
import communication.messages.InitializeThisClientsPlayerMessage;
import data.ClientPlayerQuestState;
import datasource.LevelRecord;
import datatypes.QuestStateEnum;

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
		ArrayList<ClientPlayerQuestState> expected = new ArrayList<ClientPlayerQuestState>();
		ClientPlayerQuestState q = new ClientPlayerQuestState(42, "title", "silly", QuestStateEnum.AVAILABLE, 42, 2, true, null);
		expected.add(q);
		LevelRecord level = new LevelRecord("One", 15, 10, 7);
		
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
		
		ArrayList<ClientPlayerQuestState> expected = new ArrayList<ClientPlayerQuestState>();
		ClientPlayerQuestState q = new ClientPlayerQuestState(42, "title", "silly", QuestStateEnum.AVAILABLE, 22, 13, false, null);
		expected.add(q);
		LevelRecord level = new LevelRecord("One", 15, 10, 7);
		
		CommandOverwriteQuestState x = new CommandOverwriteQuestState(new InitializeThisClientsPlayerMessage(expected, 20, level));
		x.execute();
		
		ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		assertEquals(expected, player.getQuests());
	}

}
