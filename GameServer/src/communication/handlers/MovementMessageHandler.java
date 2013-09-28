package communication.handlers;

import communication.handlers.MessageHandler;
import communication.messages.Message;
import communication.messages.MovementMessage;


/**
 * Handles a report of a player moving
 * @author merlin
 *
 */
public class MovementMessageHandler extends MessageHandler
{

	/**
	 * When one player moves, we should update the state of the engine
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		// TODO  Tell someone in the engine . . .
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return MovementMessage.class;
	}

}
