package communication;

import java.util.HashMap;

import communication.messages.Message;

/**
 * Manages a set of MessageHandlers and the type of message each one can process
 * and uses the appropriate one to process each message. It is important to note
 * that it can only store ONE handler for each message type.
 * 
 * @author merlin
 * 
 */
public class MessageHandlerSet
{

	private HashMap<Class<? extends Message>, MessageHandler> handlers;

	/**
	 * 
	 */
	public MessageHandlerSet()
	{
		handlers = new HashMap<Class<? extends Message>, MessageHandler>();
	}

	/**
	 * Register a handler for a given message type
	 * 
	 * @param class1
	 *            the type of message this handler should be used for
	 * @param handler
	 *            the handler
	 */
	public void registerHandler(Class<? extends Message> class1, MessageHandler handler)
	{
		handlers.put(class1, handler);
	}

	/**
	 * Find the appropriate handler and use it to process a given message
	 * 
	 * @param msg
	 *            the message we are supposed to process
	 * @param stateAccumulator
	 *            the state accumulator associated with the outgoing direction
	 *            for this connection which received this message
	 * @throws CommunicationException
	 *             if there is not handler for the given type of message
	 */
	public void process(Message msg, StateAccumulator stateAccumulator)
			throws CommunicationException
	{
		if (handlers.containsKey(msg.getClass()))
		{
			MessageHandler h = handlers.get(msg.getClass());
			h.process(msg, stateAccumulator);
		} else
		{
			throw new CommunicationException("No message handler for " + msg.getClass());
		}
	}

}
