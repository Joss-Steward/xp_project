package communication.handlers;

import model.PlayerManager;
import communication.handlers.MessageHandler;
import communication.messages.ConnectMessage;
import communication.messages.Message;

/**
 * Handles a message that the user is connecting to this area server
 * 
 * @author merlin
 * 
 */
public class ConnectMessageHandler extends MessageHandler
{

	/**
	 * Add this user to the player list
	 * 
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(ConnectMessage.class))
		{
			ConnectMessage cMsg = (ConnectMessage) msg;
			// assume this connection is going to work
			if (connectionManager != null)
			{
				connectionManager.setPlayerUserId(cMsg.getUserID());
			}
			PlayerManager.getSingleton().addPlayer(cMsg.getUserID(), cMsg.getPin());
			// TODO what should we do if the information in the message isn't correct
			
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ConnectMessage.class;
	}

}
