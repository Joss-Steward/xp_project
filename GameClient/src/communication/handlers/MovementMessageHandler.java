package communication.handlers;
import model.CommandMovePlayer;
import model.ModelFacade;
import communication.handlers.MessageHandler;
import communication.messages.Message;
import communication.messages.MovementMessage;


/**
 * Should process an incoming MovementMessage that is reporting that someone else moved
 * @author merlin
 *
 */
public class MovementMessageHandler extends MessageHandler
{
	/**
	 * TODO: Want to pass movement message to command. 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(MovementMessage.class))
		{
			MovementMessage movementMessage = (MovementMessage) msg;
			CommandMovePlayer cmd = new CommandMovePlayer(movementMessage.getPlayerID(),movementMessage.getPosition());
			ModelFacade.getSingleton(false).queueCommand(cmd);	
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
