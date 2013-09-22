package communication;
import communication.MessageHandler;
import communication.messages.Message;



/**
 * Should process an incoming MovementMessage that is reporting that someone else moved
 * @author merlin
 *
 */
public class PlayerJoinedMessageHandler implements MessageHandler
{

	/**
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("received " + msg);
	}

}
