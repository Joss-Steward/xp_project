package communication.handlers;

import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.Message;
import edu.ship.shipsim.client.model.ModelFacade;
import edu.ship.shipsim.client.model.CommandOverwriteQuestState;

/**
 * Handles the CurrentQuestState message
 * 
 * @author Merlin
 *
 */
public class InitializeThisClientsPlayerMessageHandler extends MessageHandler
{
	
	/**
	 * Queues the command in Model Facade
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		InitializeThisClientsPlayerMessage ourMsg = (InitializeThisClientsPlayerMessage)msg;
		CommandOverwriteQuestState cmd = new CommandOverwriteQuestState(ourMsg);
		
		ModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * @return message type that we expect
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return InitializeThisClientsPlayerMessage.class;
	}

}
