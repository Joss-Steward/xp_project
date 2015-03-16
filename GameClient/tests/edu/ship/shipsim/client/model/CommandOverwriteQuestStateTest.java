package edu.ship.shipsim.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerQuest;

import org.junit.Test;

import communication.messages.CurrentQuestStateMessage;
import datasource.PlayersForTest;
import datasource.QuestStateEnum;

public class CommandOverwriteQuestStateTest
{
	@Test
	public void constructor()
	{
		ArrayList<ClientPlayerQuest> expected = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(42, "silly", QuestStateEnum.AVAILABLE);
		expected.add(q);
		
		CommandOverwriteQuestState x = new CommandOverwriteQuestState(new CurrentQuestStateMessage(expected));
		assertEquals(expected, x.getClientPlayerQuestList());
	}
	
	@Test
	public void testExecutes()
	{
		PlayerManager pm = PlayerManager.getSingleton();
		PlayersForTest john = PlayersForTest.JOHN;
		pm.initiateLogin(john.getPlayerName(), john.getPlayerPassword());
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
		
		CommandOverwriteQuestState x = new CommandOverwriteQuestState(new CurrentQuestStateMessage(expected));
		x.execute();
		
		ThisClientsPlayer player = PlayerManager.getSingleton().getThisClientsPlayer();
		assertEquals(expected, player.getQuests());
	}

}
