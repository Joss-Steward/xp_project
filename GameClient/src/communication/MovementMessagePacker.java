package communication;
import java.util.Observable;

import model.Player;

import communication.MessagePacker;
import communication.messages.Message;
import communication.messages.MovementMessage;
import data.Position;


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
		Player player = (Player)obs;
		Message msg = new MovementMessage(player.getID(), (Position)object);
		return msg;
	}

}
