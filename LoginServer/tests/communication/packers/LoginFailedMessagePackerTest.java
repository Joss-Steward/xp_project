package communication.packers;

import static org.junit.Assert.*;
import model.reports.LoginFailedReport;

import org.junit.Test;

import communication.messages.LoginFailedMessage;
import communication.packers.LoginFailedMessagePacker;

/**
 * @author Merlin
 *
 */
public class LoginFailedMessagePackerTest
{

	/**
	 * It is enough that it builds a msg of the right type
	 */
	@Test
	public void test()
	{
		LoginFailedReport report = new LoginFailedReport();
		LoginFailedMessagePacker packer = new LoginFailedMessagePacker();
		Object msg = packer.pack(report);	
		assertEquals(LoginFailedMessage.class, msg.getClass());
	}

}
