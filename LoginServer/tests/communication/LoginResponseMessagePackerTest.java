package communication;

import static org.junit.Assert.*;
import model.reports.LoginSuccessfulReport;

import org.junit.Test;

import communication.messages.LoginResponseMessage;
import communication.packers.LoginResponseMessagePacker;

/**
 * @author Merlin
 *
 */
public class LoginResponseMessagePackerTest
{

	/**
	 * make sure it builds the right message from a given report
	 */
	@Test
	public void test()
	{
		LoginSuccessfulReport report = new LoginSuccessfulReport(42, "localhost", 1872, 123456);
		LoginResponseMessagePacker packer = new LoginResponseMessagePacker();
		LoginResponseMessage msg = (LoginResponseMessage) packer.pack(report);
		assertEquals(42, msg.getUserID());
		assertEquals("localhost", msg.getHostName());
		assertEquals(1872, msg.getPortNumber());
		assertEquals(123456, msg.getPin());
		
	}

}
