package communication.handlers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.ClientPlayerAdventure;
import model.ClientPlayerQuest;

import org.junit.Before;
import org.junit.Test;

import communication.messages.ChatMessage;
import communication.messages.CurrentQuestStateMessage;
import data.ChatType;
import data.Position;
import datasource.AdventureStateEnum;
import datasource.QuestStateEnum;
import edu.ship.shipsim.client.model.Command;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.OverwriteQuestStateCommand;

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
		CurrentQuestStateMessage msg = new CurrentQuestStateMessage(qList);
		handler.process(msg);
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
		OverwriteQuestStateCommand cmd = (OverwriteQuestStateCommand) ModelFacade.getSingleton().getNextCommand();
		ArrayList<ClientPlayerQuest> actual = cmd.getClientPlayerQuestList();
		assertEquals(qList, actual);
	}
}
