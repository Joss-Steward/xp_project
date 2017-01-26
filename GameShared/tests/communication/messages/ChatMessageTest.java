package communication.messages;

import static org.junit.Assert.*;

import org.junit.Test;

import datatypes.ChatType;
import datatypes.Position;

/**
 * Tests a ChatMessage
 * 
 * @author Andrew
 * 
 */
public class ChatMessageTest
{

	/**
	 * Test initialization of a chat message
	 */
	@Test
	public void test()
	{
		Position p = new Position(0, 0);
		ChatMessage msg = new ChatMessage("bob", "Hello World!", p, ChatType.Local);
		assertEquals("bob", msg.getSenderName());
		assertEquals("Hello World!", msg.getMessage());
		assertEquals(ChatType.Local, msg.getType());
		assertEquals(p, msg.getPosition());
	}

}
