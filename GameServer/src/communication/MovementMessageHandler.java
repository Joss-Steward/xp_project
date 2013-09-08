package communication;

import communication.messages.Message;
import communication.messages.MovementMessage;


/**
 * Handles a report of a player moving
 * @author merlin
 *
 */
public class MovementMessageHandler implements MessageHandler
{

	/**
	 * When one player moves, we should report it to the other players through the MovementNotifier
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		MovementNotifier.getSingleton().playerMoved((MovementMessage)msg);
	}

}
