package communication.messages;

import java.io.Serializable;

import data.Position;

/**
 * 
 * Create a ChatMessage that is used for sending messages.
 * 
 */
public class ChatMessage implements Message, Serializable
{
	/**
	 * Types of chat messages that can be sent
	 */
	public static enum ChatType
	{
		/**
		 * Chat is available to anyone on all servers
		 */
		World,
		/**
		 * Chat is available to anyone in this area server
		 */
		Area,
		/**
		 * Chat is only visible to nearby entities
		 */
		Local;
	}

	private static final long serialVersionUID = 1L;
	private final String message;
	private final String senderName;
	private final Position position;
	private final ChatType type;

	/**
	 * Create a chat message
	 * 
	 * @param senderName
	 *            user sending the message
	 * @param message
	 *            the message to be sent
	 * @param location
	 *            the location of the sender
	 * @param type
	 *            the type of chat message being sent
	 */
	public ChatMessage(String senderName, String message, Position location,
			ChatType type)
	{
		this.message = message;
		this.senderName = senderName;
		this.position = location;
		this.type = type;
	}

	/**
	 * @return the message sent by the chat
	 */
	public String getMessage()
	{
		return this.message;
	}

	/**
	 * @return name of the user sending the chat message
	 */
	public String getSenderName()
	{
		return this.senderName;
	}

	/**
	 * @return the location of the sender
	 */
	public Position getPosition()
	{
		return this.position;
	}

	/**
	 * @return the type of chat message being sent
	 */
	public ChatType getType()
	{
		return type;
	}

}
