package communication.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import communication.CommunicationException;
import communication.TypeDetector;
import communication.messages.Message;
/**
 * Manages a set of MessageHandlers and the type of message each one can process
 * and uses the appropriate one to process each message. It is important to note
 * that it can only store ONE handler for each message type.
 * 
 * @author merlin
 * 
 */
public class MessageHandlerSet extends TypeDetector
{

	private HashMap<Class<?>, MessageHandler> handlers;

	/**
	 * 
	 */
	public MessageHandlerSet()
	{
		handlers = new HashMap<Class<?>, MessageHandler>();
		
		ArrayList<Class<?>> handlerTypes = this.detectAllImplementorsInPackage(MessageHandler.class) ;
		for(Class<?> handlerType:handlerTypes)
		{
			try
			{
				MessageHandler handler = (MessageHandler) handlerType.newInstance();
				System.out.println("Registering Handler" + handlerType);
				registerHandler(handler);
			} catch (InstantiationException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * Register a handler for a given message type
	 * 
	 * @param handler
	 *            the handler
	 */
	public void registerHandler( MessageHandler handler)
	{
		Class<?> messageTypeWeHandle = handler.getMessageTypeWeHandle();
		handlers.put(messageTypeWeHandle, handler);
	}

	/**
	 * Find the appropriate handler and use it to process a given message
	 * 
	 * @param msg
	 *            the message we are supposed to process
	 * @throws CommunicationException
	 *             if there is not handler for the given type of message
	 */
	public void process(Message msg)
			throws CommunicationException
	{
		if (handlers.containsKey(msg.getClass()))
		{
			MessageHandler h = handlers.get(msg.getClass());
			h.process(msg);
		} else
		{
			throw new CommunicationException("No message handler for " + msg.getClass());
		}
	}

}