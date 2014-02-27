package communication.handlers;

import model.ModelFacade;
import model.MovePlayerCommand;
import communication.handlers.MessageHandler;
import communication.messages.Message;
import communication.messages.MovementMessage;


/**
 * Handles a report of a player moving
 * @author merlin
 *
 */
public class MovementMessageHandler extends MessageHandler
{

	/**
	 * When one player moves, we should update the state of the engine
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(MovementMessage.class))
		{
			MovementMessage mMsg = (MovementMessage) msg;
			MovePlayerCommand cmd = new MovePlayerCommand(mMsg.getPlayerID(), mMsg.getPosition());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return MovementMessage.class;
	}
}
