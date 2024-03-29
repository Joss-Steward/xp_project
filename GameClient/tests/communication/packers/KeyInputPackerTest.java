package communication.packers;

import static org.junit.Assert.*;
import model.reports.KeyInputSentReport;

import org.junit.Test;

import communication.messages.KeyInputMessage;

/**
 * Tests functionality for key input packer
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputPackerTest {

	/**
	 * Tests for creation of a KeyInputPacker and packing messages
	 */
	@Test
	public void testInitialization() {
		String input = "q";
		KeyInputSentReport inputReport = new KeyInputSentReport(input);
		
		KeyInputMessagePacker packer = new KeyInputMessagePacker();
		KeyInputMessage msg = (KeyInputMessage) packer.pack(inputReport);
		
		assertEquals(input, msg.getInput());
	}

}
