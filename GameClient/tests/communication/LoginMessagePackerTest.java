package communication;

import static org.junit.Assert.*;
import model.reports.LoginInitiatedReport;

import org.junit.Test;

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
		LoginMessage msg = (LoginMessage) packer.pack(new LoginInitiatedReport("harry","elizabeth"));
		assertEquals("harry", msg.getUserName());
		assertEquals("elizabeth", msg.getPassword());
	};

}
