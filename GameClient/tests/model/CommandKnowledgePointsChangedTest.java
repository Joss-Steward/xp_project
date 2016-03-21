package model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import org.junit.Test;



import communication.messages.InitializeThisClientsPlayerMessage;

import datasource.LevelRecord;

/**
 * @author Matthew Croft
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
		LevelRecord report = new LevelRecord("Weak Kolbold", 100);
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
	 */
	@Test
	public void testExecutes()
	{
//		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
//		PlayersForTest john = PlayersForTest.JOHN;
//		pm.initiateLogin(john.getPlayerName(), john.getPlayerPassword());
//
//		try
//		{
//			pm.finishLogin(john.getPlayerID());
//		} catch (AlreadyBoundException | NotBoundException e)
//		{
//			e.printStackTrace();
//			fail("Could not create this client's player from login");
//		}
//
//		ArrayList<ClientPlayerQuest> quests = new ArrayList<ClientPlayerQuest>();
//		ClientPlayerQuest q = new ClientPlayerQuest(42, "silly",
//				QuestStateEnum.AVAILABLE, 42, 4, false);
//		quests.add(q);
//		LevelRecord level = new LevelRecord("One", 15);
//		int expectedExperience = 100;
//
//		CommandOverwriteExperience x = new CommandOverwriteExperience(
//				new InitializeThisClientsPlayerMessage(quests, expectedExperience, level));
//		x.execute();
//
//		ThisClientsPlayer player = ClientPlayerManager.getSingleton().getThisClientsPlayer();
//		assertEquals(expectedExperience, player.getExperiencePoints());
//		assertEquals(level, player.getLevelRecord());
	}
}
