package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.ClientModelFacade;
import model.ClientPlayerManager;
import model.Command;
import model.CommandOverwriteExperience;
import model.CommandOverwriteQuestState;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import communication.messages.InitializeThisClientsPlayerMessage;
import data.AdventureStateEnum;
import data.QuestStateEnum;
import datasource.LevelRecord;

/**
 * @author Frank Schmidt
 *
 */
public class InitializeThisClientsPlayerMessageHandlerTest
{

	/**
	 * Reset the ModelFacade
	 */
	@Before
	public void setUp()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
	}

	/**
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		InitializeThisClientsPlayerMessageHandler h = new InitializeThisClientsPlayerMessageHandler();
		assertEquals(InitializeThisClientsPlayerMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * We should add a command to the ModelFacade command queue
	 * 
	 * @throws InterruptedException
	 *             shouldn't
	 * @throws NotBoundException
	 *             shouldn't
	 * @throws AlreadyBoundException
	 *             shouldn't
	 */
	@Test
	public void test() throws InterruptedException, AlreadyBoundException,
			NotBoundException
	{
		ClientPlayerManager.getSingleton().initiateLogin("john", "pw");
		ClientPlayerManager.getSingleton().finishLogin(PlayersForTest.JOHN.getPlayerID());
		InitializeThisClientsPlayerMessageHandler handler = new InitializeThisClientsPlayerMessageHandler();
		ArrayList<ClientPlayerQuest> qList = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(3, "questtitle", "stupid quest",
				QuestStateEnum.TRIGGERED, 42, 133, true);
		q.addAdventure(new ClientPlayerAdventure(3, "stupid adventure", 5,
				AdventureStateEnum.TRIGGERED, false, true, "My big toe", QuestStateEnum.AVAILABLE));
		qList.add(q);
		LevelRecord level = new LevelRecord("One", 45, 10, 7);
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(
				qList, 20, level);
		handler.process(msg);

		assertEquals(2, ClientModelFacade.getSingleton().getCommandQueueLength());
		for (int i = 0; i < ClientModelFacade.getSingleton().getCommandQueueLength(); i++)
		{
			Command cmd = ClientModelFacade.getSingleton().getNextCommand();
			if (cmd.getClass() == CommandOverwriteQuestState.class)
			{
				CommandOverwriteQuestState thisCmd = (CommandOverwriteQuestState) (cmd);
				assertEquals(qList, thisCmd.getClientPlayerQuestList());
			} else if (cmd.getClass() == CommandOverwriteExperience.class)
			{
				CommandOverwriteExperience thisCmd = (CommandOverwriteExperience) (cmd);
				assertEquals(20, thisCmd.getExperiencePoints());
				assertEquals(PlayersForTest.JOHN.getPlayerID(), thisCmd.getPlayerID());
				assertEquals(level, thisCmd.getLevelRecord());
			} else
			{
				fail("Unexpected command in facade queue " + cmd);
			}
		}
		ClientModelFacade.getSingleton().emptyQueue();
	}

}
