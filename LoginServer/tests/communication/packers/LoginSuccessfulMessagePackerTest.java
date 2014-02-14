package communication.packers;

import static org.junit.Assert.*;
import model.reports.LoginSuccessfulReport;

import org.junit.Test;

import communication.messages.LoginSuccessfulMessage;
import communication.packers.LoginSuccessfulMessagePacker;

/**
 * @author Merlin
 *
 */
public class LoginSuccessfulMessagePackerTest
{

	/**
	 * make sure it builds the right message from a given report
	 */
	@Test
	public void test()
	{
		LoginSuccessfulReport report = new LoginSuccessfulReport(42, "localhost", 1872, 0.123456);
		LoginSuccessfulMessagePacker packer = new LoginSuccessfulMessagePacker();
		LoginSuccessfulMessage msg = (LoginSuccessfulMessage) packer.pack(report);
		assertEquals(42, msg.getPlayerID());
		assertEquals("localhost", msg.getHostName());
		assertEquals(1872, msg.getPortNumber());
		assertEquals(0.123456, msg.getPin(),0.000001);
		
	}

}
