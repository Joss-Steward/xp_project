package communication.handlers;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.OptionsManager;
import model.PlayerManager;

import org.junit.Before;
import org.junit.Test;

import communication.StateAccumulator;
import communication.messages.LoginFailedMessage;
import communication.messages.LoginMessage;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.Message;
import datasource.PlayersForTest;

/**
 * @author Merlin
 * 
 */
public class LoginMessageHandlerTest
{

	/**
	 * reset the singleton before each test
	 */
	@Before
	public void setup()
	{
		OptionsManager.resetSingleton();
		OptionsManager.getSingleton(true);
		PlayerManager.resetSingleton();
	}

	/**
	 * 
	 */
	@Test
	public void tellsTheModel()
	{
		LoginMessageHandler handler = new LoginMessageHandler();
		StateAccumulator accum = new StateAccumulator(null);
		handler.setAccumulator(accum);
		handler.process(new LoginMessage(PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getPlayerPassword()));
		assertEquals(1, PlayerManager.getSingleton().getNumberOfPlayers());
	}
	
	/**
	 * Make sure that the login message handler queues the appropriate response for successful login
	 */
	@Test
	public void queuesResonse()
	{
		LoginMessageHandler handler = new LoginMessageHandler();
		StateAccumulator accum = new StateAccumulator(null);
		handler.setAccumulator(accum);
		handler.process(new LoginMessage(PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getPlayerPassword()));
		
		ArrayList<Message> x = accum.getPendingMsgs();
		LoginSuccessfulMessage response = (LoginSuccessfulMessage) x.get(0);
		assertEquals(PlayersForTest.MERLIN.getPlayerID(), response.getPlayerID());
		assertEquals("localhost", response.getHostName());
		assertEquals(1872, response.getPortNumber());
		//Can't really check the pin since it is randomly generated
//		assertEquals(?, response.getPin(), 0.000001);
	}
	
	/**
	 * Make sure that the login message handler queues the appropriate response for successful login
	 */
	@Test
	public void queuesResonseFailure()
	{
		LoginMessageHandler handler = new LoginMessageHandler();
		StateAccumulator accum = new StateAccumulator(null);
		handler.setAccumulator(accum);
		handler.process(new LoginMessage(PlayersForTest.MERLIN.getPlayerName(),
				PlayersForTest.MERLIN.getPlayerPassword() + 'Z'));
		
		ArrayList<Message> x = accum.getPendingMsgs();
		assertEquals(LoginFailedMessage.class, x.get(0).getClass());
		
	}
}
