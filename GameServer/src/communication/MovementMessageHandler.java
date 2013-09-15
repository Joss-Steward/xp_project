package communication;

import communication.messages.Message;


/**
 * Handles a report of a player moving
 * @author merlin
 *
 */
public class MovementMessageHandler implements MessageHandler
{

	/**
	 * When one player moves, we should update the state of the engine
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		// TODO  Tell someone in the engine . . .
	}

}
