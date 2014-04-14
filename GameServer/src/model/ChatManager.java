package model;

import model.reports.SendChatMessageReport;
import data.ChatType;
import data.Position;

/**
 * @author Dave
 *
 * Handles broadcasting chat from one client out to everyone.
 */
public class ChatManager extends QualifiedObservable
{
	private static ChatManager me;
	
	/**
	 * Singleton constructor
	 */
	private ChatManager()
	{
		reportTypes.add(SendChatMessageReport.class);
		registerReportTypesWeNotify();
	}
	
	
	/**
	 * There can be only one.
	 * 
	 * @return The current instance of the singleton
	 */
	public static synchronized ChatManager getSingleton()
	{
		if(me == null)
		{
			me = new ChatManager();
		}
		
		return me;
	}
	
	
	/**
	 * Reset the singleton
	 */
	public static void resetSingleton()
	{
		me = null;
	}
	
	
	/**
	 * Broadcasts the chat message out to all connected clients
	 * @param message The text of the message
	 * @param senderName The name of the player who sent the message
	 * @param pos The position of the player when they sent the message
	 * @param type The type of chat message this is
	 */
	public void sendChatToClients(String message, String senderName, Position pos, ChatType type)
	{
		SendChatMessageReport report = new SendChatMessageReport(message, senderName, pos, type);
		
		this.notifyObservers(report);
	}
}