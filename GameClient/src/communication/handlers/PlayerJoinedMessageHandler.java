package communication.handlers;
import communication.handlers.MessageHandler;
import communication.messages.Message;
import communication.messages.PlayerJoinedMessage;



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

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return PlayerJoinedMessage.class;
	}

}
