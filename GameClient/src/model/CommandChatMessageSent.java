package model;

import data.ChatType;

/**
 * @author Matthew Kujawski
 *
 *Receives the message parameters and passes them to the ChatManager 
 */
public class CommandChatMessageSent extends Command
{
	private String message;
	private ChatType type;
	private boolean isValidMessage;
	
	/**
	 * @param message is the message that has been sent from UI
	 */
	public CommandChatMessageSent(String message)
	{
		parseMessage(message);
	}
	
	/**
	 * Translate the literal String from the UI into a message type and message
	 * @param msg The literal String message from the UI
	 */
	private void parseMessage(String msg)
	{
		isValidMessage = true;
		
		int splitPoint = msg.indexOf(' ');
		String msgType = msg.substring(0, splitPoint);
		
		this.message = msg.substring(splitPoint + 1);

		if(msgType.equals("/w")) //world chat
		{
			this.type = ChatType.World;
		}else if(msgType.equals("/z")) //zone chat
		{
			this.type = ChatType.Zone;
		}else if(msgType.equals("/l")) //local chat
		{
			this.type = ChatType.Local;
		}else //malformed message
		{
			isValidMessage = false;
		}
	}
	
	/**
	 * @return the message that was entered by the player
	 */
	public String getMessage() 
	{
		return message;
	}

	/**
	 * @return the type of message
	 */
	public ChatType getType() 
	{
		return type;
	}
	
	/**
	 * @return True if the literal String given to this command was formatted correctly with respect to message type
	 */
	public boolean getValidity()
	{
		return isValidMessage;
	}

	/**
	 * Sends information about a chat message to the ChatManager
	 */
	@Override
	public boolean execute()
	{
		boolean successful;
		
		if(isValidMessage)
		{
			ChatManager.getSingleton().sendChatToServer(message, type);
			successful = true;
		}else
		{
			//malformed message, so don't pass it on
			successful = false;
		}
		
		return successful;
	}
}
