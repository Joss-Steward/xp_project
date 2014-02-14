package communication.handlers;

import model.AddPlayerCommand;
import model.ModelFacade;
import communication.handlers.MessageHandler;
import communication.messages.ConnectMessage;
import communication.messages.Message;

/**
 * Handles a message that the player is connecting to this area server
 * 
 * @author merlin
 * 
 */
public class ConnectMessageHandler extends MessageHandler
{

	/**
	 * Add this player to the player list
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
				connectionManager.setPlayerID(cMsg.getPlayerID());
			}
			AddPlayerCommand cmd = new AddPlayerCommand(cMsg.getPlayerID(), cMsg.getPin());
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
