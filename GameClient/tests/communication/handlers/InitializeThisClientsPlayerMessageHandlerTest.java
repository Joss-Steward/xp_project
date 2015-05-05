package communication.handlers;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;

import org.junit.Before;
import org.junit.Test;

import communication.messages.InitializeThisClientsPlayerMessage;
import datasource.AdventureStateEnum;
import datasource.LevelRecord;
import datasource.PlayersForTest;
import datasource.QuestStateEnum;
import edu.ship.shipsim.client.model.CommandOverwriteExperience;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.CommandOverwriteQuestState;
import edu.ship.shipsim.client.model.PlayerManager;

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
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
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
	 * 				shouldn't
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void test() throws InterruptedException, AlreadyBoundException, NotBoundException
	{
		PlayerManager.getSingleton().initiateLogin("john", "pw");
		PlayerManager.getSingleton().finishLogin(PlayersForTest.JOHN.getPlayerID());
		InitializeThisClientsPlayerMessageHandler handler = new InitializeThisClientsPlayerMessageHandler();
		ArrayList<ClientPlayerQuest> qList = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(3, "stupid quest", QuestStateEnum.TRIGGERED, 42, 133); 
		q.addAdventure(new ClientPlayerAdventure(3, "stupid adventure", 5, AdventureStateEnum.PENDING, false));
		qList.add(q);
		LevelRecord level = new LevelRecord("One", 45);
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(qList, 20, level);
		handler.process(msg);
		assertEquals(2, ModelFacade.getSingleton().getCommandQueueLength());
		while(ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		CommandOverwriteQuestState cmd = (CommandOverwriteQuestState) ModelFacade.getSingleton().getNextCommand();
		ArrayList<ClientPlayerQuest> actual = cmd.getClientPlayerQuestList();
		assertEquals(qList, actual);
		assertEquals(42, q.getExperiencePointsGained());
		assertEquals(133, q.getAdventuresToFulfillment());
	}
	
	/**
	 * We should add a command  to the ModelFacade command queue
	 * 
	 * @throws InterruptedException
	 * 				shouldn't
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void testExperiencePts() throws InterruptedException, AlreadyBoundException, NotBoundException
	{
		PlayerManager.getSingleton().initiateLogin("john", "pw");
		PlayerManager.getSingleton().finishLogin(PlayersForTest.JOHN.getPlayerID());
		InitializeThisClientsPlayerMessageHandler handler = new InitializeThisClientsPlayerMessageHandler();
		ArrayList<ClientPlayerQuest> qList = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(3, "stupid quest", QuestStateEnum.TRIGGERED, 42, 8); 
		q.addAdventure(new ClientPlayerAdventure(3, "stupid adventure", 5, AdventureStateEnum.PENDING, false));
		qList.add(q);
		int expectedPoints = 20;
		LevelRecord level = new LevelRecord("One", 45);
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(qList, expectedPoints, level);
		handler.process(msg);
		assertEquals(2, ModelFacade.getSingleton().getCommandQueueLength());
		ModelFacade.getSingleton().getNextCommand();
		CommandOverwriteExperience cmd = (CommandOverwriteExperience) ModelFacade.getSingleton().getNextCommand();
		int actualPoints = cmd.getExperiencePoints();
		assertEquals(expectedPoints, actualPoints);
		while(ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
	}
}
