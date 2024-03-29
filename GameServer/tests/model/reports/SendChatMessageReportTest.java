package model.reports;

import static org.junit.Assert.assertEquals;
import model.reports.SendChatMessageReport;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;

/**
 * @author Dave
 *
 *         Make sure that the SendChatMessageReport behaves properly.
 */
public class SendChatMessageReportTest
{
	/**
	 * The report should correctly remember the information it was given.
	 */
	@Test
	public void testInit()
	{
		String msg = "Test message";
		String sender = "Bob";
		Position pos = new Position(0, 1);
		ChatType type = ChatType.World;

		SendChatMessageReport report = new SendChatMessageReport(msg, sender, pos, type);

		assertEquals(msg, report.getMessage());
		assertEquals(sender, report.getSenderName());
		assertEquals(pos, report.getPosition());
		assertEquals(type, report.getType());
	}

	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(SendChatMessageReport.class).verify();
	}
}
