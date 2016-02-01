package communication.packers;

import static org.junit.Assert.assertEquals;
import model.reports.SendChatMessageReport;

import org.junit.Test;

import communication.messages.ChatMessage;
import data.ChatType;
import data.Position;

/**
 * @author Dave
 *
 * Make sure that the ChatMessagePacker behaves properly.
 */
public class ChatMessagePackerTest
{
	
	/**
	 * Make sure that the report is properly translated into the message.
	 */
	@Test
	public void testPacking()
	{
		String sender = "Billy-Bob";
		String text = "Hello world";
		Position loc = new Position(0,0);
		ChatType type = ChatType.Local;
		
		SendChatMessageReport report = new SendChatMessageReport(text, sender, loc, type);
		ChatMessagePacker packer = new ChatMessagePacker();
		ChatMessage msg = (ChatMessage) packer.pack(report);
		
		assertEquals(sender, msg.getSenderName());
		assertEquals(text, msg.getMessage());
		assertEquals(loc, msg.getPosition());
		assertEquals(type, msg.getType());
	}

}
