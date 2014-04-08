package communication.handlers;

import communication.ConnectionManager;
import communication.StateAccumulator;
import communication.messages.Message;

/**
 * MessageHandlers are used to process incoming messages. For each message type,
 * a MessageHander that processes that message must be registered with the
 * MessageProcessor on this side of the communication protocols.
 * 
 * @author merlin
 * 
 */
public abstract class MessageHandler
{
	protected ConnectionManager connectionManager;
	private StateAccumulator stateAccumulator;

	/**
	 * Processes an incoming message
	 * 
	 * @param msg
	 *            the message to handle
	 */
	public abstract void process(Message msg);

	/**
	 * get the type of message this handler can process
	 * 
	 * @return the type of message
	 */
	public abstract Class<?> getMessageTypeWeHandle();

	/**
	 * @param connectionManager
	 *            the state accumulator associated with this hander's connection
	 */
	public void setConnectionManager(ConnectionManager connectionManager)
	{
		this.connectionManager = connectionManager;
	}

	protected ConnectionManager getConnectionManager()
	{
		return connectionManager;
	}

	protected void setAccumulator(StateAccumulator stateAccumulator)
	{
		this.stateAccumulator = stateAccumulator;
	}

	/**
	 * get the accumulator that we should use for responses to incoming messages
	 * @return the state accumulator
	 */
	public StateAccumulator getStateAccumulator()
	{
		return stateAccumulator;
	}
}
