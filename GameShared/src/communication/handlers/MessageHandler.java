package communication.handlers;
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
	 */
	public void process (Message msg);
	
	/**
	 * get the type of message this handler can process
	 * @return the type of message
	 */
	public Class<?>  getMessageTypeWeHandle();
}
