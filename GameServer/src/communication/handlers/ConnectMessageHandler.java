package communication.handlers;

import model.CommandAddPlayer;
import model.ModelFacade;

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
			if (getConnectionManager() != null)
			{
				System.out.println("setting player");
				getConnectionManager().setPlayerID(cMsg.getPlayerID());
			}
			CommandAddPlayer cmd = new CommandAddPlayer(cMsg.getPlayerID(), cMsg.getPin());
			
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
