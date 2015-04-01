package edu.ship.shipsim.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerQuest;

import org.junit.Test;

import communication.messages.InitializeThisClientsPlayerMessage;
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
		ClientPlayerQuest q = new ClientPlayerQuest(42, "silly", QuestStateEnum.AVAILABLE);
		expected.add(q);
		
		CommandOverwriteQuestState x = new CommandOverwriteQuestState(new InitializeThisClientsPlayerMessage(expected));
		assertEquals(expected, x.getClientPlayerQuestList());
	}
	
	/**
	 * Test the execute of the command and make sure that this client
	 * player quest list is overwritten
	 */
	@Test
	public void testExecutes()
	{
		PlayerManager pm = PlayerManager.getSingleton();
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
		ClientPlayerQuest q = new ClientPlayerQuest(42, "silly", QuestStateEnum.AVAILABLE);
		expected.add(q);
		
		CommandOverwriteQuestState x = new CommandOverwriteQuestState(new InitializeThisClientsPlayerMessage(expected));
		x.execute();
		
		ThisClientsPlayer player = PlayerManager.getSingleton().getThisClientsPlayer();
		assertEquals(expected, player.getQuests());
	}

}
