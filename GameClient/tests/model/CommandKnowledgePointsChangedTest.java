package model;

import static org.junit.Assert.*;

import java.nio.channels.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import org.junit.Test;









import testData.PlayersForTest;
import communication.messages.InitializeThisClientsPlayerMessage;
import data.QuestStateEnum;
import datasource.LevelRecord;

/**
 * @author Matthew Croft
 * @author Emily Maust
 *
 */
public class CommandKnowledgePointsChangedTest
{

	/**
	 * Basic initialization for this command
	 */
	@Test
	public void testInitialization()
	{
		int knowledgePoints = 100;
		LevelRecord report = new LevelRecord("Weak Kolbold", 100, 0, 0);
		ArrayList<ClientPlayerQuest> list = new ArrayList<ClientPlayerQuest>();
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(
				list, 0, knowledgePoints, report);
		CommandKnowledgePointsChanged ow = new CommandKnowledgePointsChanged(msg);
		assertEquals(knowledgePoints, ow.getKnowledge());
		assertEquals(report, ow.getRecord());
	}

	/**
	 * Test the execute of the command and make sure that this client player
	 * experience is overwritten
	 * @throws java.rmi.AlreadyBoundException h
	 */
	@Test
	public void testExecutes() throws java.rmi.AlreadyBoundException
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

		ArrayList<ClientPlayerQuest> quests = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(42, "title",
				"silly", QuestStateEnum.AVAILABLE, 42, 4, false);
		quests.add(q);
		LevelRecord level = new LevelRecord("One", 15, 0, 0);
		int knowledgePoints = 100;

		CommandKnowledgePointsChanged x = new CommandKnowledgePointsChanged(
				new InitializeThisClientsPlayerMessage(quests, 0, knowledgePoints, level));
		x.execute();

		ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		assertEquals(knowledgePoints , player.getKnowledgePoints());
		assertEquals(level, player.getLevelRecord());
	}
}
