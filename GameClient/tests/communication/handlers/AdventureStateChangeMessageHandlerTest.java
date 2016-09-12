package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ClientModelFacade;
import model.ClientPlayerManager;
import model.CommandAdventureStateChange;

import org.junit.Before;
import org.junit.Test;

import testData.PlayersForTest;
import communication.messages.AdventureStateChangeMessage;
import datatypes.AdventureStateEnum;

/**
 * Tests functionality of the AdventureStateChangeMessageHandler
 * @author Ryan
 *
 */
public class AdventureStateChangeMessageHandlerTest 
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
	public void testTypeWeHandle()
	{
		AdventureStateChangeMessageHandler h = new AdventureStateChangeMessageHandler();
		assertEquals(AdventureStateChangeMessage.class, h.getMessageTypeWeHandle());
	}
	

	/**
	 * Test that the handler messages handles the messages and creates
	 * a command
	 * @throws InterruptedException shouldn't
	 * @throws NotBoundException shouldn't
	 * @throws AlreadyBoundException shouldn't
	 */
	@Test
	public void testMessageHandling() throws InterruptedException, AlreadyBoundException, NotBoundException
	{
		ClientPlayerManager.getSingleton().initiateLogin("john", "pw");
		ClientPlayerManager.getSingleton().finishLogin(PlayersForTest.JOHN.getPlayerID());
		AdventureStateChangeMessageHandler h = new AdventureStateChangeMessageHandler();
		AdventureStateChangeMessage msg = new AdventureStateChangeMessage(1, 2, 3, "Big Adventure", AdventureStateEnum.TRIGGERED, true, "Grammy");
		
		h.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		CommandAdventureStateChange cmd = (CommandAdventureStateChange) ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(2, cmd.getQuestID());
		assertEquals(3, cmd.getAdventureID());
		assertEquals("Big Adventure", cmd.getAdventureDescription());
		assertEquals(AdventureStateEnum.TRIGGERED, cmd.getAdventureState());
		assertTrue(cmd.isRealLifeAdventure());
		assertEquals("Grammy", cmd.getWitnessTitle());
		ClientModelFacade.getSingleton().emptyQueue();
	}

}
