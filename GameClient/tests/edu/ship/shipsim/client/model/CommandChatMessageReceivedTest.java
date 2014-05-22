package edu.ship.shipsim.client.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import data.ChatType;
import data.Position;
import edu.ship.shipsim.client.model.CommandChatMessageReceived;

/**
 * @author Matthew Kujawski
 *
 */
public class CommandChatMessageReceivedTest 
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