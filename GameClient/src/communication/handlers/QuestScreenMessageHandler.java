package communication.handlers;

import communication.messages.Message;
import communication.messages.QuestScreenMessage;
import edu.ship.shipsim.client.model.CommandQuestScreenClose;
import edu.ship.shipsim.client.model.CommandQuestScreenOpen;
import edu.ship.shipsim.client.model.ModelFacade;

/**
 * 
 * @author Joshua
 * 
 */
public class QuestScreenMessageHandler extends MessageHandler
{

	/**
	 * 
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		System.out.println("hello from client quest screen message handler");
		System.out.println("received " + msg);
		if (msg.getClass().equals(QuestScreenMessage.class))
		{

			QuestScreenMessage rMsg = (QuestScreenMessage) msg;

			if (rMsg.isLoadState() == false)
			{
				ModelFacade.getSingleton().queueCommand(new CommandQuestScreenClose());
			} else
			{
				ModelFacade.getSingleton().queueCommand(new CommandQuestScreenOpen());
			}
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return QuestScreenMessage.class;
	}

}
