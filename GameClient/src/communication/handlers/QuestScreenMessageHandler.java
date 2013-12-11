package communication.handlers;

import model.CommandQuestScreenClose;
import model.CommandQuestScreenOpen;
import model.ModelFacade;
import communication.messages.Message;
import communication.messages.QuestScreenMessage;

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

			if (rMsg.getLoadState() == false)
			{
				ModelFacade.getSingleton(false).queueCommand(
						new CommandQuestScreenClose());
			} else
			{
				ModelFacade.getSingleton(false).queueCommand(
						new CommandQuestScreenOpen());
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
