package communication.handlers;

import static org.junit.Assert.*;
import model.PlayerManager;

import org.junit.Before;
import org.junit.Test;

import communication.messages.LoginMessage;


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
		PlayerManager.resetSingleton();
	}
	
	/**
	 * 
	 */
	@Test
	public void tellsTheModel()
	{
		LoginMessageHandler handler = new LoginMessageHandler();
		handler.process(new LoginMessage("fred","pw"));
		assertEquals(1, PlayerManager.getSingleton().getNumberOfPlayers());
	}
}
