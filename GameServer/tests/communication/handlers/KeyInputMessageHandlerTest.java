package communication.handlers;

import static org.junit.Assert.*;
import model.ModelFacade;

import org.junit.Test;

import communication.messages.KeyInputMessage;

/**
 * Tests functionality for a key input message handler
 * @author Ian Keefer & TJ Renninger
 *
 */
public class KeyInputMessageHandlerTest
{

	/**
	 * Tests the creation and process of a key input message handler. Tests to see the command size has a new command in it.
	 */
	@Test
	public void testProcessKeyInputMessage() {
		ModelFacade.resetSingleton();
		
		String input = "q";
		KeyInputMessage message = new KeyInputMessage(input);
		KeyInputMessageHandler handler = new KeyInputMessageHandler();
		handler.process(message);
		assertEquals(1, ModelFacade.getSingleton().queueSize());

	}

}
