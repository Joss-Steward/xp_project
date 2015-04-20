package edu.ship.shipsim.client.model.reports;

import data.ChatType;

import model.QualifiedObservableReport;

/**
 * Report for when a chat message is received
 * 
 * @author Steve
 *
 */
public class ChatReceivedReport implements QualifiedObservableReport
{
	private final String message;
	private final String senderName;
	private final ChatType type;
	
	/**
	 * 
	 * @param message The message we received
	 * @param senderName Who sent the message
	 * @param type What type of message this is
	 */
	public ChatReceivedReport(String message, String senderName, ChatType type)
	{
		this.message = message;
		this.senderName = senderName;
		this.type = type;
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		boolean equals = true;
		ChatReceivedReport other = (ChatReceivedReport) obj;
		equals = equals && message.equals(other.message);
		equals = equals && senderName.equals(other.senderName);
		equals = equals && type.equals(other.type);

		return equals;
	}
	
	/**
	 * @return the readable form of the message for the chat history
	 */
	public String toString()
	{
		return "["+this.senderName+"] "+this.message;
	}
	
	/**
	 * @return the type of message this chat is broadcast as
	 */
	public ChatType getType()
	{
		return this.type;
	}
}
