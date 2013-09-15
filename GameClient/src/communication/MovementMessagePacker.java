package communication;
import model.QualifiedObservableReport;
import communication.MessagePacker;
import communication.messages.Message;


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
	public Message pack(QualifiedObservableReport object)
	{
//		Player player = (Player)obs;
//		Message msg = new MovementMessage(player.getID(), (Position)object);
		return null;
	}

}
