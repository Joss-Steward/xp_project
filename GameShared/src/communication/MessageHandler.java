package communication;
import communication.messages.Message;


/**
 * MessageHandlers are used to process incoming messages.  For each message type, a MessageHander that processes that message must be registered with
 * the MessageProcessor on this side of the communication protocols.
 * 
 * @author merlin
 *
 */
public interface MessageHandler
{
	/**
	 * Processes an incoming message
	 * @param msg the message to handle
	 * @param stateAccumulator the accumulator associated with the outgoing direction for the connection which received this message
	 */
	public void process (Message msg);
}
