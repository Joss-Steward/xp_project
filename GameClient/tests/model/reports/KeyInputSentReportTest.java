package model.reports;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests user key input report
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputSentReportTest {

	/**
	 * Tests creation of a KeyInputSentReport
	 */
	@Test
	public void testInitialization() {
		String input = "q";
		KeyInputSentReport report = new KeyInputSentReport(input);
		assertEquals(input, report.getInput());
	}

}
