package communication;

import static org.junit.Assert.*;
import model.PlayerManager;

import org.junit.Before;
import org.junit.Test;


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
		fail("what does it do?");
	}
}
