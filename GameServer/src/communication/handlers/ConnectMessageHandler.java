package communication.handlers;

import model.AddPlayerCommand;
import model.ModelFacade;
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
			if (connectionManager != null)
			{
				connectionManager.setPlayerUserId(cMsg.getUserID());
			}
			AddPlayerCommand cmd = new AddPlayerCommand(cMsg.getUserID(), cMsg.getPin());
			ModelFacade.getSingleton().queueCommand(cmd);
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
