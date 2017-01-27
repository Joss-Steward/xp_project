/**
 * 
 */
package model;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.CommandOverwriteExperience;
import model.ClientPlayerManager;
import model.ThisClientsPlayer;

import org.junit.Test;

import testData.PlayersForTest;
import communication.messages.InitializeThisClientsPlayerMessage;
import data.ClientPlayerQuestState;
import datasource.LevelRecord;
import datatypes.QuestStateEnum;

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
		LevelRecord report = new LevelRecord("Weak Kolbold", 100, 10, 7);
		ArrayList<ClientPlayerQuestState> list = new ArrayList<ClientPlayerQuestState>();
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(
				list, expPoints, report);
		CommandOverwriteExperience ow = new CommandOverwriteExperience(msg);
		assertEquals(expPoints, ow.getExperiencePoints());
		assertEquals(report, ow.getLevelRecord());
	}

	/**
	 * Test the execute of the command and make sure that this client player
	 * experience is overwritten
	 */
	@Test
	public void testExecutes()
	{
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		PlayersForTest john = PlayersForTest.JOHN;
		pm.initiateLogin(john.getPlayerName(), john.getPlayerPassword());

		try
		{
			pm.finishLogin(john.getPlayerID());
		} catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("Could not create this client's player from login");
		}

		ArrayList<ClientPlayerQuestState> quests = new ArrayList<ClientPlayerQuestState>();
		ClientPlayerQuestState q = new ClientPlayerQuestState(42, "title",
				"silly", QuestStateEnum.AVAILABLE, 42, 4, false, null);
		quests.add(q);
		LevelRecord level = new LevelRecord("One", 15, 10, 7);
		int expectedExperience = 100;

		CommandOverwriteExperience x = new CommandOverwriteExperience(
				new InitializeThisClientsPlayerMessage(quests, expectedExperience, level));
		x.execute();

		ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		assertEquals(expectedExperience, player.getExperiencePoints());
		assertEquals(level, player.getLevelRecord());
	}
}
