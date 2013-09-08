package communication;
import java.util.Observable;

import communication.MessagePacker;
import communication.messages.LoginMessage;
import communication.messages.Message;


/**
 * Takes the information given to us when MovementNotifier updates and translates it to the appropriate MovementMessage.
 * It turns out this is pretty trivial since the MovementNotifier pushes a MovementMessage
 * @author merlin
 *
 */
public class LoginMessagePacker implements MessagePacker
{

	/**
	 * 
	 */
	@Override
	public Message pack(Observable obs, Object object)
	{
		Message msg = new LoginMessage((String)object);
		return msg;
	}

}
