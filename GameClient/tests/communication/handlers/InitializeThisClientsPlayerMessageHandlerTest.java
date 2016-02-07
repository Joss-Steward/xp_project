package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;
import model.ClientModelFacade;
import model.ClientPlayerManager;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import communication.messages.InitializeThisClientsPlayerMessage;
import datasource.AdventureStateEnum;
import datasource.LevelRecord;
import datasource.QuestStateEnum;

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
	 * 				shouldn't
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void test() throws InterruptedException, AlreadyBoundException, NotBoundException
	{
		ClientPlayerManager.getSingleton().initiateLogin("john", "pw");
		ClientPlayerManager.getSingleton().finishLogin(PlayersForTest.JOHN.getPlayerID());
		InitializeThisClientsPlayerMessageHandler handler = new InitializeThisClientsPlayerMessageHandler();
		ArrayList<ClientPlayerQuest> qList = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(3, "stupid quest", QuestStateEnum.TRIGGERED, 42, 133); 
		q.addAdventure(new ClientPlayerAdventure(3, "stupid adventure", 5, AdventureStateEnum.TRIGGERED, false));
		qList.add(q);
		LevelRecord level = new LevelRecord("One", 45);
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(qList, 20, level);
		handler.process(msg);
		while(ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		ArrayList<ClientPlayerQuest> actual = ClientPlayerManager.getSingleton().getThisClientsPlayer().getQuests();
		assertEquals(qList, actual);
		
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
		ClientPlayerManager.getSingleton().initiateLogin("john", "pw");
		ClientPlayerManager.getSingleton().finishLogin(PlayersForTest.JOHN.getPlayerID());
		InitializeThisClientsPlayerMessageHandler handler = new InitializeThisClientsPlayerMessageHandler();
		ArrayList<ClientPlayerQuest> qList = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(3, "stupid quest", QuestStateEnum.TRIGGERED, 42, 8); 
		q.addAdventure(new ClientPlayerAdventure(3, "stupid adventure", 5, AdventureStateEnum.TRIGGERED, false));
		qList.add(q);
		int expectedPoints = 20;
		LevelRecord level = new LevelRecord("One", 45);
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(qList, expectedPoints, level);
		handler.process(msg);
		
		while(ClientModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
		assertEquals(expectedPoints, ClientPlayerManager.getSingleton().getThisClientsPlayer().getExperiencePoints());
	}
}
