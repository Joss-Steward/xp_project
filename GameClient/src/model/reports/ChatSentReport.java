package model.reports;

import communication.messages.ChatMessage.ChatType;
import data.Position;
import model.QualifiedObservableReport;

/**
 * Report for when a chat message is sent
 * 
 * @author Steve
 *
 */
public class ChatSentReport implements QualifiedObservableReport
{
	private String message;
	private String senderName;
	private Position position;
	private ChatType type;
	
	/**
	 * 
	 * @param message The message we received
	 * @param senderName Who sent the message
	 * @param position Location of sender
	 * @param type What type of message this is
	 */
	public ChatSentReport(String message, String senderName, Position position, ChatType type)
	{
		this.message = message;
		this.senderName = senderName;
		this.position = position;
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
		ChatSentReport other = (ChatSentReport) obj;
		equals = equals && message.equals(other.message);
		equals = equals && senderName.equals(other.senderName);
		equals = equals && position.equals(other.position);
		equals = equals && type.equals(other.type);

		return equals;
	}
}
