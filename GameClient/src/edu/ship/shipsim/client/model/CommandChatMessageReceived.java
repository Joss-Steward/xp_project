package edu.ship.shipsim.client.model;

import data.ChatType;
import data.Position;
/**
 * @author Matthew Kujawski
 *
 *Receives the message parameters and passes them to the ChatManager 
 */
public class CommandChatMessageReceived extends Command
{
	private String message;
	private String senderName;
	private Position location;
	private ChatType type;
	
	/**
	 * @param senderName is the name of the player who sent the message
	 * @param message is the message that will be displayed by the UI
	 * @param location of the player when the message was sent
	 * @param type is the type of message: local, world, area
	 */
	public CommandChatMessageReceived(String senderName, String message, Position location, ChatType type)
	{
		this.senderName = senderName;
		this.message = message;
		this.location = location;
		this.type = type;
	}
	
	/**
	 * @return the message that was received the server
	 */
	public String getMessage() 
	{
		return message;
	}

	/**
	 * @return the name of the player that sent the message
	 */
	public String getSenderName() 
	{
		return senderName;
	}

	/**
	 * @return the location of the player when the message was sent
	 */
	public Position getLocation() 
	{
		return location;
	}

	/**
	 * @return the type of message
	 */
	public ChatType getType() 
	{
		return type;
	}

	/**
	 * The ChatManager will call the sendChatToUI method with the following parameters.
	 */
	@Override
	public boolean execute()
	{
		ChatManager.getSingleton().sendChatToUI(message, senderName, location, type);
		return true;
	}
}