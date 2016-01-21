package model;

import static org.junit.Assert.*;
import model.CommandChatMessageSent;

import org.junit.Test;

import data.ChatType;

/**
 * @author Matthew Kujawski and Dave Abrams
 *These set of test makes sure that the CommandChatMessageSent works properly.
 */
public class CommandChatMessageSentTest 
{

	/**
	 * This test makes sure that the command can properly break a String from the UI apart into message content and message type.
	 */
	@Test
	public void testInstantiation() 
	{
		String localMessage = "/l This is a local message";
		CommandChatMessageSent ccms = new CommandChatMessageSent(localMessage);
		assertTrue(ccms.getValidity());
		assertEquals(ChatType.Local, ccms.getType());
		assertEquals("This is a local message", ccms.getMessage());
		assertTrue(ccms.execute());
		
		String zoneMsg = "/z test";
		CommandChatMessageSent ccms2 = new CommandChatMessageSent(zoneMsg);
		assertEquals(ChatType.Zone, ccms2.getType());
		
		String worldMsg = "/w test";
		CommandChatMessageSent ccms3 = new CommandChatMessageSent(worldMsg);
		assertEquals(ChatType.World, ccms3.getType());
	}
	
	/**
	 * Checks to make sure that messages that are not formatted correctly by the user or UI are identified.
	 */
	@Test
	public void testInvalidChatMessages() 
	{
		String invalidMessage = "/2 This is an invalid message";
		CommandChatMessageSent ccms = new CommandChatMessageSent(invalidMessage);
		assertFalse(ccms.getValidity());
		assertFalse(ccms.execute());
		
		String invalidMessage2 = "This is an invalid message";
		CommandChatMessageSent ccms2 = new CommandChatMessageSent(invalidMessage2);
		assertFalse(ccms2.getValidity());
		assertFalse(ccms2.execute());
	}
	
}
