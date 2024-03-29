package communication.handlers;

import model.CommandQuestStateChange;
import model.ClientModelFacade;
import communication.messages.Message;
import communication.messages.QuestStateChangeMessage;

/**
 * Handles the QuestStateChange Message
 * 
 * @author Ryan
 *
 */
public class QuestStateChangeMessageHandler extends MessageHandler 
{
	/**
	 * Queues the command in Model Facade
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg) 
	{
		QuestStateChangeMessage ourMsg = (QuestStateChangeMessage)msg;
		CommandQuestStateChange cmd = new CommandQuestStateChange(ourMsg);
		ClientModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * @return the message we expect
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	public Class<?> getMessageTypeWeHandle() 
	{
		return QuestStateChangeMessage.class;
	}
}
