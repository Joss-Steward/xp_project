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
	private boolean reaction;

	/**
	 * @param source
	 *            the server that should send the message
	 * @param destination
	 *            the server that should receive the message
	 * @param message
	 *            the message that should be sent
	 * @param reaction
	 *            true if this message should be generated by the source as a
	 *            response to previous actions in the sequence. False means that
	 *            the message is part of the test with the goal of causing some
	 *            behavior in the recipient
	 */
	public MessageFlow(ServerType source, ServerType destination, Message message,
			boolean reaction)
	{
		super();
		this.message = message;
		this.source = source;
		this.destination = destination;
		this.reaction = reaction;
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

	/**
	 * @return true if this message is a response to the actions in the sequence
	 *         false if it is in the test only to cause subsequent behavior
	 */
	public boolean isReaction()
	{
		return reaction;
	}

}
