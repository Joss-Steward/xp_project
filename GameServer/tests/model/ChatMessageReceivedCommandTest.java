package model;

import static org.junit.Assert.assertEquals;
import model.CommandChatMessageReceived;

import org.junit.Test;

import data.ChatType;
import data.Position;

/**
 * @author Josh
 *
 */
public class ChatMessageReceivedCommandTest 
{

	/**
	 * Making sure that the CommandChatMessageReceived has the correct information
	 */
	@Test
	public void testCreateTheReportWithCorrectMessage() 
	{
		String message = "Hello";
		String senderName = "Dave";
		Position location = new Position(1,1);
		ChatType type = ChatType.Local;
		
		CommandChatMessageReceived ccmr = new CommandChatMessageReceived(senderName, message, location, type);
		
		assertEquals(message, ccmr.getMessage());
		assertEquals(senderName, ccmr.getSenderName());
		assertEquals(location, ccmr.getLocation());
		assertEquals(type, ccmr.getType());
	}
}