package communication.handlers;

import model.PlayerManager;
import communication.handlers.MessageHandler;
import communication.messages.Message;
import communication.messages.PlayerJoinedMessage;

/**
 * Should process an incoming MovementMessage that is reporting that someone
 * else moved
 * 
 * @author merlin
 * 
 */
public class PlayerJoinedMessageHandler extends MessageHandler
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
		PlayerJoinedMessage playerJoinedMessage = (PlayerJoinedMessage)msg;
		PlayerManager.getSingleton().addPlayer(playerJoinedMessage.getPlayerName());
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return PlayerJoinedMessage.class;
	}

}
