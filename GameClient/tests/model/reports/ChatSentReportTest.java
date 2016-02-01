package model.reports;

import static org.junit.Assert.assertEquals;
import model.reports.ChatSentReport;

import org.junit.Test;

import data.ChatType;
import data.Position;

/**
 * @author Dave
 * 
 * Make sure that the ChatSentReport behaves properly.
 */
public class ChatSentReportTest
{
	/**
	 * Make sure that the report properly holds the information it was given.
	 */
	@Test
	public void testInit()
	{
		String text = "Hello world";
		String name = "Bob";
		Position loc = new Position(1,1);
		ChatType type = ChatType.Local;
		
		ChatSentReport report = new ChatSentReport(text, name, loc, type);
		
		assertEquals(text, report.getMessage());
		assertEquals(name, report.getSenderName());
		assertEquals(loc, report.getPosition());
		assertEquals(type, report.getType());
	}
}