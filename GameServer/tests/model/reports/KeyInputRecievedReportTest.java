package model.reports;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Ian Keefer
 * @author TJ Renninger
 *
 */
public class KeyInputRecievedReportTest {

	/**
	 * Test to see that a key input report is created.
	 */
	@Test
	public void testInitialization() {
		String input = "q";
		KeyInputRecievedReport report = new KeyInputRecievedReport(input);
		assertEquals(input, report.getInput());
	}

}
