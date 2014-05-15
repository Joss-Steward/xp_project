package communication.handlers;

import model.CommandRemovePlayer;
import model.ModelFacade;

import communication.messages.DisconnectMessage;
import communication.messages.Message;

/**
 * 
 * @author nhydock
 *
 */
public class DisconnectMessageHandler extends MessageHandler
{
	/**
	 * remove this player from the player list
	 * 
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(DisconnectMessage.class))
		{
			DisconnectMessage cMsg = (DisconnectMessage) msg;
			System.err.println("removing player");
			if (connectionManager != null)
			{
				System.err.println("setting player");
				connectionManager.setPlayerID(cMsg.getPlayerID());
			}
			
			CommandRemovePlayer cmd = new CommandRemovePlayer(cMsg.getPlayerID());
			
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return DisconnectMessage.class;
	}
}
