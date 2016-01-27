package model;

import communication.messages.Message;

/**
 * Defines how one message should flow through the system
 * 
 * @author Merlin
 *
 */
public class MessageFlow
{

	private Message message;
	private ServerType source;
	private ServerType destination;

	/**
	 * @param source
	 *            the server that should send the message
	 * @param destination
	 *            the server that should receive the message
	 * @param message
	 *            the message that should be sent
	 */
	public MessageFlow(ServerType source,
			ServerType destination, Message message)
	{
		super();
		this.message = message;
		this.source = source;
		this.destination = destination;
	}

	/**
	 * @return the server to which this message should be sent
	 */
	public ServerType getDestination()
	{
		return destination;
	}

	/**
	 * @return the message that should be sent
	 */
	public Message getMessage()
	{
		return message;
	}

	/**
	 * @return the server who should send the message
	 */
	public ServerType getSource()
	{
		return source;
	}

}
