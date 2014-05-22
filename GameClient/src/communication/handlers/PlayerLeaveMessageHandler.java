package communication.handlers;

import communication.handlers.MessageHandler;
import communication.messages.Message;
import communication.messages.PlayerLeaveMessage;
import edu.ship.shipsim.client.model.CommandRemovePlayer;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * Should process an incoming PlayerJoinedMessage that is reporting that someone
 * joined our area server
 * 
 * @author merlin
 * 
 */
public class PlayerLeaveMessageHandler extends MessageHandler
{

	/**
	 * A player has joined our area server, so notify the PlayerManager of his
	 * presence
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("received " + msg);
		if (msg.getClass().equals(PlayerLeaveMessage.class))
		{
			PlayerLeaveMessage realMsg = (PlayerLeaveMessage) msg;
			CommandRemovePlayer cmd = new CommandRemovePlayer(realMsg.getPlayerID());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
		
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return PlayerLeaveMessage.class;
	}

}
