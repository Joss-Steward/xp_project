package model.reports;

import data.ChatType;
import data.Position;
import model.QualifiedObservableReport;

/**
 * Carries information from a chat message from the ChatManager to the ChatMessagePacker.
 * 
 * @author Dave
 */
public final class SendChatMessageReport implements QualifiedObservableReport
{
	private final String text;
	private final String name;
	private final Position location;
	private final ChatType type;
	/**
	 * @param messageContent The text of the message
	 * @param playerName The name of the player who sent the message
	 * @param pos The position of the player when they sent the message
	 * @param type The type of chat message this is
	 */
	public SendChatMessageReport(String messageContent, String playerName, Position pos, ChatType type)
	{
		this.text = messageContent;
		this.name = playerName;
		this.location = pos;
		this.type = type;
	}
	
	/**
	 * @return The position of the player when they sent the message
	 */
	public Position getPosition() 
	{
		return location;
	}

	/**
	 * @return The name of the player who sent the message
	 */
	public String getSenderName() 
	{
		return name;
	}

	/**
	 * @return The text of the message
	 */
	public String getMessage() 
	{
		return text;
	}

	/**
	 * @return The type of chat message this is
	 */
	public ChatType getType() 
	{
		return type;
	}

	/**
	 * Override the default hashCode() so that instances of this class can be
	 * properly compared with one another.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	/**
	 * Override the default equals() so that instances of this class can be
	 * properly compared.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SendChatMessageReport other = (SendChatMessageReport) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
