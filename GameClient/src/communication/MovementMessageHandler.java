package communication;
import communication.MessageHandler;
import communication.StateAccumulator;
import communication.messages.Message;



/**
 * Should process an incoming MovementMessage that is reporting that someone else moved
 * @author merlin
 *
 */
public class MovementMessageHandler implements MessageHandler
{

	/**
	 * 
	 * @see MessageHandler#process(Message,StateAccumulator)
	 */
	@Override
	public void process(Message msg, StateAccumulator accumulator)
	{
		System.out.println("received " + msg);
	}

}
