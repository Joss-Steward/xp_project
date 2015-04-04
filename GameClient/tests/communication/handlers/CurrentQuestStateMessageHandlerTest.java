package communication.handlers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;

import org.junit.Before;
import org.junit.Test;

import communication.messages.InitializeThisClientsPlayerMessage;
import datasource.AdventureStateEnum;
import datasource.LevelRecord;
import datasource.QuestStateEnum;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.CommandOverwriteQuestState;

/**
 * @author Frank Schmidt
 *
 */
public class CurrentQuestStateMessageHandlerTest 
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
		CurrentQuestStateMessageHandler h = new CurrentQuestStateMessageHandler();
		assertEquals(InitializeThisClientsPlayerMessage.class, h.getMessageTypeWeHandle());
	}
	
	/**
	 * We should add a command to the ModelFacade command queue
	 * 
	 * @throws InterruptedException
	 * 				shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		CurrentQuestStateMessageHandler handler = new CurrentQuestStateMessageHandler();
		ArrayList<ClientPlayerQuest> qList = new ArrayList<ClientPlayerQuest>();
		ClientPlayerQuest q = new ClientPlayerQuest(3, "stupid quest", QuestStateEnum.TRIGGERED); 
		q.addAdventure(new ClientPlayerAdventure(3, "stupid adventure", AdventureStateEnum.PENDING));
		qList.add(q);
		LevelRecord level = new LevelRecord("One", 15);
		InitializeThisClientsPlayerMessage msg = new InitializeThisClientsPlayerMessage(qList, 20, level);
		handler.process(msg);
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
		CommandOverwriteQuestState cmd = (CommandOverwriteQuestState) ModelFacade.getSingleton().getNextCommand();
		ArrayList<ClientPlayerQuest> actual = cmd.getClientPlayerQuestList();
		assertEquals(qList, actual);
	}
}
