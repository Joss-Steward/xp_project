package communication.handlers;

import communication.messages.AreaCollisionMessage;
import communication.messages.Message;

/**
 * Handles Area Collision messages from the client and creates
 * a command to change the state of the model.
 * 
 * @author Ethan
 *
 */
public class AreaCollisionMessageHandler extends MessageHandler
{

	/**
	 * Process the message from AreaCollsionMessage and create a command.
	 * 
	 * @param msg the message from AreaCollisionMessage
	 */
	@Override
	public void process(Message msg) {	
	}

	/**
	 * @return returns the type of class this message deals with.
	 */
	@Override
	public Class<?> getMessageTypeWeHandle() {
		return AreaCollisionMessage.class;
	}

}
