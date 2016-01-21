package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import model.ModelFacade;
import model.PlayerManager;

import org.junit.Before;
import org.junit.Test;

import communication.messages.AdventureStateChangeMessage;
import datasource.AdventureStateEnum;
import datasource.PlayersForTest;

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
		ModelFacade.resetSingleton();
		ModelFacade.getSingleton(true, false);
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
		PlayerManager.getSingleton().initiateLogin("john", "pw");
		PlayerManager.getSingleton().finishLogin(PlayersForTest.JOHN.getPlayerID());
		AdventureStateChangeMessageHandler h = new AdventureStateChangeMessageHandler();
		AdventureStateChangeMessage msg = new AdventureStateChangeMessage(1, 2, 3, "Big Adventure", AdventureStateEnum.TRIGGERED);
		
		h.process(msg);
		assertEquals(1, ModelFacade.getSingleton().getCommandQueueLength());
		while(ModelFacade.getSingleton().hasCommandsPending())
		{
			Thread.sleep(100);
		}
	}

}
