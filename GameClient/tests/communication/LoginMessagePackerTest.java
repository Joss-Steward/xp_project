package communication;

import static org.junit.Assert.*;

import model.Player;

import org.junit.Test;

import communication.LoginMessagePacker;
import communication.messages.LoginMessage;

/**
 * 
 * @author merlin
 *
 */
public class LoginMessagePackerTest
{

	/**
	 * Just make sure the right stuff goes into the message when we pack it
	 */
	@Test
	public void test()
	{
		LoginMessagePacker packer = new LoginMessagePacker();
		LoginMessage msg = (LoginMessage) packer.pack(Player.getSingleton(), "harry");
		assertEquals("harry", msg.getUserName());
	};

}
