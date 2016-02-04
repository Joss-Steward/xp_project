package communication.handlers;

import model.CommandMovePlayer;
import model.ClientModelFacade;
import communication.handlers.MessageHandler;
import communication.messages.Message;
import communication.messages.PlayerMovedMessage;

/**
 * Should process an incoming MovementMessage that is reporting that someone
 * else moved
 * 
 * @author merlin
 * 
 */
public class MovementMessageHandler extends MessageHandler
{
	/**
	 * 
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(PlayerMovedMessage.class))
		{
			PlayerMovedMessage movementMessage = (PlayerMovedMessage) msg;
			CommandMovePlayer cmd = new CommandMovePlayer(movementMessage.getPlayerID(),
					movementMessage.getPosition());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return PlayerMovedMessage.class;
	}

}
