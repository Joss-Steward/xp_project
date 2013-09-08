package communication;
import java.util.Observable;

import communication.messages.Message;
import communication.messages.MovementMessage;

/**
 * Takes the information given to us when MovementNotifier updates and translates it to the appropriate MovementMessage.
 * It turns out this is pretty trivial since the MovementNotifier pushes a MovementMessage
 * @author merlin
 *
 */
public class MovementMessagePacker implements MessagePacker
{

	/**
	 * 
	 */
	@Override
	public Message pack(Observable obs, Object object)
	{
		return (MovementMessage)object;
	}

}
