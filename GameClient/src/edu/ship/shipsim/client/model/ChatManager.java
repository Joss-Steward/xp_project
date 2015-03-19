package edu.ship.shipsim.client.model;

import java.util.Observable;

import model.QualifiedObservableConnector;
import data.ChatType;
import data.Position;
import edu.ship.shipsim.client.model.reports.ChatReceivedReport;
import edu.ship.shipsim.client.model.reports.ChatSentReport;

/**
 * Handles all chat reporting and messaging
 * 
 * @author Steve
 * 
 */
public class ChatManager extends Observable 
{
	/**
	 * The square radius that local chat can reach from a position
	 */
	private static int LOCAL_CHAT_RADIUS = 5;

	private static ChatManager singleton;

	/**
	 * Protected for singleton
	 */
	private ChatManager() 
	{
	
	}

	/**
	 * There should be only one
	 * 
	 * @return the only player
	 */
	public static synchronized ChatManager getSingleton() 
	{
		if (singleton == null) {
			singleton = new ChatManager();
		}
		return singleton;
	}

	/**
	 * Reset the singleton but don't instantiate a new one
	 */
	public static synchronized void resetSingleton() 
	{
		singleton = null;
	}

	/**
	 * 
	 * @param message chat message to send to the ui
	 * @param senderName name of the player who sent the message
	 * @param position position of the player who sent the message
	 * @param type the type of this message
	 */
	public void sendChatToUI(String message, String senderName, Position position, ChatType type) 
	{
		ChatReceivedReport report = new ChatReceivedReport(message, senderName,	type);

		if (type.equals(ChatType.Zone)) 
		{
			QualifiedObservableConnector.getSingleton().sendReport(this, report);
		} else if (type.equals(ChatType.Local)) 
		{
			if (this.canReceiveLocalMessage(position)) 
			{
				QualifiedObservableConnector.getSingleton().sendReport(this, report);
			}
		}
	}

	/**
	 * Send a report for a message this client generated, fetching the proper
	 * player name and location
	 * 
	 * @param message chat message to send to the ui
	 * @param type the type of this message
	 */
	public void sendChatToServer(String message, ChatType type) 
	{
		Player thisPlayer = PlayerManager.getSingleton().getThisClientsPlayer();
		ChatSentReport report = new ChatSentReport(message,
				thisPlayer.getName(), thisPlayer.getPosition(), type);
		QualifiedObservableConnector.getSingleton().sendReport(this, report);
	}

	/**
	 * When receiving a local message check if the player is close enough to
	 * hear
	 * 
	 * @param position position of the sender
	 */
	protected boolean canReceiveLocalMessage(Position position) 
	{
		Position myPosition = PlayerManager.getSingleton()
				.getThisClientsPlayer().getPosition();
		
		return Math.abs(myPosition.getColumn() - position.getColumn()) <= LOCAL_CHAT_RADIUS
				&& Math.abs(myPosition.getRow() - position.getRow()) <= LOCAL_CHAT_RADIUS;
	}
}