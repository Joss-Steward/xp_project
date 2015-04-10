/**
 * 
 */
package edu.ship.shipsim.client.model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerQuest;

import org.junit.Test;

import communication.messages.InitializeThisClientsPlayerMessage;
import datasource.LevelRecord;
import datasource.PlayersForTest;
import datasource.QuestStateEnum;

/**
 * @author nk3668
 *
 */
public class CommandOverwriteExperienceTest 
{
	/**
	 * Basic initialization for this command
	 */
	@Test
	public void testInitialization()
	{
		int expPoints = 1000;
		LevelRecord report = new LevelRecord("Weak Kolbold", 100);
		ArrayList<ClientPlayerQuest> list = new ArrayList<ClientPlayerQuest>();
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(list, expPoints, report);
		CommandOverwriteExperience ow = new CommandOverwriteExperience(msg);
		assertEquals(expPoints, ow.getExperiencePoints());
		assertEquals(report, ow.getLevelRecord());
	}
	
	/**
	 * Test the execute of the command and make sure that this client
	 * player experience is overwritten
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
		
		ArrayList<ClientPlayerQuest> quests = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(42, "silly", QuestStateEnum.AVAILABLE, 42, 4);
		quests.add(q);
		LevelRecord level = new LevelRecord("One", 15);
		int expectedExperience = 100;
				
		CommandOverwriteExperience x = new CommandOverwriteExperience(new InitializeThisClientsPlayerMessage(quests, expectedExperience, level));
		x.execute();
		
		ThisClientsPlayer player = PlayerManager.getSingleton().getThisClientsPlayer();
		assertEquals(expectedExperience, player.getExperiencePoints());
		assertEquals(level, player.getLevelRecord());
	}
}
