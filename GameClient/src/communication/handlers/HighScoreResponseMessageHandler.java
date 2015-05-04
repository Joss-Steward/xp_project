package communication.handlers;

import communication.messages.HighScoreResponseMessage;
import communication.messages.Message;
import edu.ship.shipsim.client.model.CommandHighScoreResponse;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * Receives a message High Score Response
 * @author nk3668
 *
 */
public class HighScoreResponseMessageHandler extends MessageHandler
{

	/**
	 * Returns what type of object this class handles
	 * @return class type
	 */
	public Class<?> getMessageTypeWeHandle() 
	{
		return HighScoreResponseMessage.class;
	}

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg) 
	{
		if (msg.getClass().equals(HighScoreResponseMessage.class))
		{
			HighScoreResponseMessage message = (HighScoreResponseMessage) msg;
			CommandHighScoreResponse cmd = new CommandHighScoreResponse(message.getScoreReports());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

}
