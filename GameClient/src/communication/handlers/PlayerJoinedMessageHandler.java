package communication.handlers;

import model.CommandInitializePlayer;
import model.ModelFacade;
import communication.handlers.MessageHandler;
import communication.messages.Message;
import communication.messages.PlayerJoinedMessage;

/**
 * Should process an incoming PlayerJoinedMessage that is reporting that someone
 * joined our area server
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
		PlayerJoinedMessage playerJoinedMessage = (PlayerJoinedMessage) msg;
		CommandInitializePlayer cmd = new CommandInitializePlayer(
				playerJoinedMessage.getPlayerID(), playerJoinedMessage.getPlayerName(),
				playerJoinedMessage.getAppearanceType(),
				playerJoinedMessage.getPosition());
		ModelFacade.getSingleton().queueCommand(cmd);
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
