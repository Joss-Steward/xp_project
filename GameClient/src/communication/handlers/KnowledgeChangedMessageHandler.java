package communication.handlers;

import model.ClientModelFacade;
import model.CommandKnowledgePointsChanged;
import communication.messages.KnowledgeChangedMessage;
import communication.messages.Message;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 */
public class KnowledgeChangedMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg) 
	{
		if (msg.getClass().equals(KnowledgeChangedMessage.class))
		{
			KnowledgeChangedMessage knowledgeChangedMessage = (KnowledgeChangedMessage) msg;
			CommandKnowledgePointsChanged cmd = new CommandKnowledgePointsChanged(knowledgeChangedMessage.getPlayerID(),knowledgeChangedMessage.getKnowledgePoints());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}
		
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle() {
		// TODO Auto-generated method stub
		return KnowledgeChangedMessage.class;
	}

}
