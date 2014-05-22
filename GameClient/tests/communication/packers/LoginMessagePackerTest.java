package communication.packers;

import static org.junit.Assert.*;

import org.junit.Test;

import communication.messages.LoginMessage;
import communication.packers.LoginMessagePacker;
import edu.ship.shipsim.client.model.reports.LoginInitiatedReport;

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
		LoginMessage msg = (LoginMessage) packer.pack(new LoginInitiatedReport("harry",
				"elizabeth"));
		assertEquals("harry", msg.getPlayerName());
		assertEquals("elizabeth", msg.getPassword());
	};

}
